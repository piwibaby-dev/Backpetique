package org.generation.backpetique.services;

import org.generation.backpetique.dtos.PaymentRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Payment;
import org.generation.backpetique.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
    }

    public Payment createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setAmount(request.amount());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, PaymentRequest request) {
        Payment existingPayment = getPaymentById(id);
        existingPayment.setOrderId(request.orderId());
        existingPayment.setPaymentMethod(request.paymentMethod());
        existingPayment.setAmount(request.amount());
        return paymentRepository.save(existingPayment);
    }

    public Payment deletePaymentById(Long id) {
        Payment existingPayment = getPaymentById(id);
        paymentRepository.delete(existingPayment);
        return existingPayment;
    }
}