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

    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.orderId());
        payment.setPaymentMethod(paymentRequest.paymentMethod());
        payment.setAmount(paymentRequest.amount());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment existingPayment = getPaymentById(id);
        if(paymentRequest.orderId() != null) existingPayment.setOrderId(paymentRequest.orderId());
        if(paymentRequest.paymentMethod() != null)existingPayment.setPaymentMethod(paymentRequest.paymentMethod());
        if(paymentRequest.amount() != null) existingPayment.setAmount(paymentRequest.amount());
        return paymentRepository.save(existingPayment);
    }

    public Payment deletePaymentById(Long id) {
        Payment existingPayment = getPaymentById(id);
        paymentRepository.delete(existingPayment);
        return existingPayment;
    }
}