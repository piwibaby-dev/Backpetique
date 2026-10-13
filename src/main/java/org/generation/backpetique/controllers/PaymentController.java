package org.generation.backpetique.controllers;

import jakarta.validation.Valid;
import org.generation.backpetique.dtos.PaymentRequest;
import org.generation.backpetique.models.Payment;
import org.generation.backpetique.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentList);
    }

    @GetMapping(path = "{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("paymentId") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.createPayment(paymentRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.getId())
                .toUri();
        return ResponseEntity.created(location).body(payment);
    }

    @PutMapping(path = "{paymentId}")
    public ResponseEntity<Payment> updatePaymentById(@PathVariable("paymentId") Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.updatePayment(id, paymentRequest);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping(path = "{paymentId}")
    public ResponseEntity<Payment> deletePaymentById(@PathVariable("paymentId") Long id) {
        Payment payment = paymentService.deletePaymentById(id);
        return ResponseEntity.ok(payment);
    }
}