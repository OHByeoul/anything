package com.just.anything.api;

import com.just.anything.domain.Order;
import com.just.anything.repository.OrderRepository;
import com.just.anything.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SimpleOrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orders(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        orders.stream().map(o->o.getMember().getName())
                .collect(Collectors.toList()); //lazy 강제 초기화
        return orders; // 엔티티로 리턴할때, 순환참조 피해보기1
    }

}
