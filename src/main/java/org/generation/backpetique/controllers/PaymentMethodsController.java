package org.generation.backpetique.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PaymentMethodsRequest;
import org.generation.backpetique.models.PaymentMethods;
import org.generation.backpetique.services.PaymentMethodsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class PaymentMethodsController {
    private final PaymentMethodsService paymentMethodsService;

    @GetMapping(path = "/users/{userId}/payment-methods")
    public ResponseEntity<List<PaymentMethods>> getByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(paymentMethodsService.getByUserId(userId));
    }

    @PostMapping(path = "/users/{userId}/payment-methods")
    public ResponseEntity<PaymentMethods> create(@PathVariable("userId") Long userId, @Valid @RequestBody PaymentMethodsRequest request) {
        PaymentMethods method = paymentMethodsService.create(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/payment-methods/{id}")
                .buildAndExpand(method.getId())
                .toUri();
        return ResponseEntity.created(location).body(method);
    }

    @PutMapping(path = "/payment-methods/{id}")
    public ResponseEntity<PaymentMethods> update(@PathVariable("id") Long id, @Valid @RequestBody PaymentMethodsRequest request) {
        return ResponseEntity.ok(paymentMethodsService.update(id, request));
    }

    @DeleteMapping(path = "/payment-methods/{id}")
    public ResponseEntity<PaymentMethods> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentMethodsService.delete(id));
    }
}
