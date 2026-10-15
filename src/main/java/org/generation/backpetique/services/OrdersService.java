package org.generation.backpetique.services;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.OrdersRequest;
import org.generation.backpetique.dtos.PetsRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.*;
import org.generation.backpetique.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final UsersRepository userRepository;
    private final PaymentRepository paymentRepository;

    //Traer todas las ordenes
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    //Obtener una orden por id
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Orden con el id " + id + " no encontrada")
        );
    }



    //Crear una orden
    public Orders createOrder(OrdersRequest newOrder) {
        Users user = userRepository.findById(newOrder.userId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + newOrder.userId() + " no encontrado")
        );


//        Payment payment = paymentRepository.findById(newOrder.paymentId()).orElseThrow(
//                () -> new ResourceNotFoundException("Pago con el id " + newOrder.paymentId() + " no encontrado")
//        );


        Orders order = new Orders();
        order.setTotalAmount(newOrder.totalAmount());
        order.setStatusO(newOrder.statusO() != null ? newOrder.statusO() : "PENDING");
        order.setOrderDate(LocalDateTime.now());
        order.setUserId(user);
//        order.setPaymentId(payment);
        //order.setPaymentId(newOrder.paymentId());

        return orderRepository.save(order);
    }



    //Actualizar una orden
    public Orders updateOrder(Long id, OrdersRequest updateOrder) {
        Orders oldOrder = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Orden con el id " + id + " no encontrada")
        );

        if (updateOrder.totalAmount() != null) {
            oldOrder.setTotalAmount(updateOrder.totalAmount());
        }
        if (updateOrder.statusO() != null) {
            oldOrder.setStatusO(updateOrder.statusO());
        }
        if (updateOrder.userId() != null) {
            Users user = userRepository.findById(updateOrder.userId()).orElseThrow(
                    () -> new ResourceNotFoundException("Usuario con el id " + updateOrder.userId() + " no encontrado")
            );
            oldOrder.setUserId(user);
        }
//        if (updateOrder.paymentId() != null) {
//            Payment payment = paymentRepository.findById(updateOrder.paymentId()).orElseThrow(
//                    () -> new ResourceNotFoundException("Pago con el id " + updateOrder.paymentId() + " no encontrado")
//            );
//            oldOrder.setPaymentId(payment);
//        }

        return orderRepository.save(oldOrder);
    }



    //Eliminar una orden
    public Orders deleteOrder(Long id) {
        Orders order = getOrderById(id);
        orderRepository.delete(order);
        return order;
    }

}
