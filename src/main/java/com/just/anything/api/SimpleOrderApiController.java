package com.just.anything.api;

import com.just.anything.domain.Order;
import com.just.anything.dto.SimpleOrderDto;
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

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orders2(){ // dto로 반환
        return  orderRepository.findAllByString(new OrderSearch())
                .stream().map(SimpleOrderDto::new) //o->new SimpleOrderDto(o)
                .collect(Collectors.toList());

    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orders3() {
        return orderRepository.findAllWithMemberAndDelivery()
                .stream().map(o->new SimpleOrderDto(o))
                .collect(Collectors.toList());

    }

}
