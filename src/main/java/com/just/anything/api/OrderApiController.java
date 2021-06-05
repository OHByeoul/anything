package com.just.anything.api;

import com.just.anything.domain.Order;
import com.just.anything.domain.OrderItem;
import com.just.anything.repository.OrderRepository;
import com.just.anything.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orders1(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o->o.getItem().getName());
        }
        return orders;
    }
}
