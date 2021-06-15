package com.just.anything.api;

import com.just.anything.domain.Order;
import com.just.anything.domain.OrderItem;
import com.just.anything.dto.OrderDto;
import com.just.anything.dto.OrderFlatDto;
import com.just.anything.repository.OrderRepository;
import com.just.anything.repository.OrderSearch;
import com.just.anything.repository.order.query.OrderQueryDto2;
import com.just.anything.repository.order.query.OrderQueryRepository2;
import com.just.anything.repository.orderdtoquery.OrderQueryDto;
import com.just.anything.repository.orderdtoquery.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryRepository2 orderQueryRepository2;

    @GetMapping("/api/v1/orders") //엔티티 노출하는 방법
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

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orders2(){ // dto로 반환
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
                .map(o->new OrderDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orders3(){ // dto로 반환
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(o->new OrderDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orders3_1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ){ // dto로 반환
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
                .map(o->new OrderDto(o)).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto2> orders4(){
        return orderQueryRepository2.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto2> orders5(){

        return orderQueryRepository2.findOrderQueryDtos_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> orders6(){
        return orderQueryRepository.findAllByDto_flat();
    }
}
