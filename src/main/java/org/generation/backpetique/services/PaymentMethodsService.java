package org.generation.backpetique.services;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PaymentMethodsRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.PaymentMethods;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.repository.PaymentMethodsRepository;
import org.generation.backpetique.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodsService {

    private final PaymentMethodsRepository paymentMethodsRepository;
    private final UsersRepository usersRepository;

    public List<PaymentMethods> getByUserId(Long userId) {
        if (!usersRepository.existsById(userId)) throw new ResourceNotFoundException("Usuario con el id " + userId + " no encontrado");
        return paymentMethodsRepository.findByUserId_IdOrderByIsDefaultDescIdAsc(userId);
    }

    public PaymentMethods getById(Long id) {
        return paymentMethodsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Método de pago con el id " + id + " no encontrado")
        );
    }

    @Transactional
    public PaymentMethods create(Long userId, PaymentMethodsRequest request) {
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + userId + " no encontrado")
        );
        if (request.type() == null) throw badRequest("El tipo de método de pago es obligatorio");
        if (request.type().equals("Tarjeta")
                && (request.holderName() == null || request.brand() == null || request.last4() == null || request.expiry() == null)) {
            throw badRequest("Para una tarjeta se necesita titular, marca, últimos 4 dígitos y vencimiento");
        }
        if (request.type().equals("PayPal") && request.paypalEmail() == null) {
            throw badRequest("Para PayPal se necesita el correo de la cuenta");
        }

        List<PaymentMethods> existentes = paymentMethodsRepository.findByUserId_IdOrderByIsDefaultDescIdAsc(userId);
        boolean predeterminado = existentes.isEmpty() || Boolean.TRUE.equals(request.isDefault());
        if (predeterminado) quitarPredeterminados(existentes);

        PaymentMethods method = new PaymentMethods();
        method.setType(request.type());
        method.setAlias(request.alias());
        method.setHolderName(request.holderName());
        method.setBrand(request.brand());
        method.setLast4(request.last4());
        method.setExpiry(request.expiry());
        method.setPaypalEmail(request.paypalEmail());
        method.setIsDefault(predeterminado);
        method.setUserId(user);
        return paymentMethodsRepository.save(method);
    }

    @Transactional
    public PaymentMethods update(Long id, PaymentMethodsRequest request) {
        PaymentMethods method = getById(id);
        if (request.alias() != null) method.setAlias(request.alias());
        if (request.holderName() != null) method.setHolderName(request.holderName());
        if (request.expiry() != null) method.setExpiry(request.expiry());
        if (request.paypalEmail() != null) method.setPaypalEmail(request.paypalEmail());
        if (Boolean.TRUE.equals(request.isDefault())) {
            quitarPredeterminados(paymentMethodsRepository.findByUserId_IdOrderByIsDefaultDescIdAsc(method.getUserId().getId()));
            method.setIsDefault(true);
        }
        return paymentMethodsRepository.save(method);
    }

    @Transactional
    public PaymentMethods delete(Long id) {
        PaymentMethods method = getById(id);
        Long userId = method.getUserId().getId();
        paymentMethodsRepository.delete(method);
        if (Boolean.TRUE.equals(method.getIsDefault())) {
            paymentMethodsRepository.findByUserId_IdOrderByIsDefaultDescIdAsc(userId).stream()
                    .filter(otro -> !otro.getId().equals(id))
                    .findFirst()
                    .ifPresent(otro -> {
                        otro.setIsDefault(true);
                        paymentMethodsRepository.save(otro);
                    });
        }
        return method;
    }

    private void quitarPredeterminados(List<PaymentMethods> methods) {
        methods.stream().filter(m -> Boolean.TRUE.equals(m.getIsDefault())).forEach(m -> {
            m.setIsDefault(false);
            paymentMethodsRepository.save(m);
        });
    }

    private ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
