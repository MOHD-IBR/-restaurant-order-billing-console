package com.restaurant.service;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private static final double TAX_RATE = 0.05; // 5% tax

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        // Calculate totals
        double subtotal = 0;
        for (OrderItem item : order.getOrderItems()) {
            subtotal += item.getTotal();
        }

        double tax = subtotal * TAX_RATE;
        double grandTotal = subtotal + tax;

        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setGrandTotal(grandTotal);

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
