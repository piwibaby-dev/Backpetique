package org.generation.backpetique.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.OrdersRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Orders;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.repository.OrdersRepository;
import org.generation.backpetique.services.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;

    /**
     * GET /api/orders
     * http://localhost:8080/api/orders
     * Obtiene todas las ordenes
     */
    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    /**
     * GET /api/orders/{id}
     * http://localhost:8080/api/orders/1
     * Obtiene una orden por su id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ordersService.getOrderById(id));
    }

    /**
     * POST /api/orders/{id}
     * http://localhost:8080/api/orders/1
     * Crea una nueva orden
     */
    @PostMapping
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody OrdersRequest newOrder) {
        Orders createdOrder = ordersService.createOrder(newOrder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdOrder);
    }

    /**
     * PUT /api/orders/{id}
     * http://localhost:8080/api/orders/1
     * Actualiza una orden
     */
    @PutMapping("{id}")
    public ResponseEntity<Orders> updateOrder(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrdersRequest updateData) {
        Orders updatedOrder = ordersService.updateOrder(id, updateData);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * DELETE /api/orders/{id}
     * http://localhost:8080/api/orders/1
     * Elimina una orden
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Orders> deleteOrder(@PathVariable("id") Long id) {
        Orders orders = ordersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Orden con el id " + id + " no encontrada")
        );
        ordersRepository.delete(orders);
        return ResponseEntity.ok(orders);
    }
}