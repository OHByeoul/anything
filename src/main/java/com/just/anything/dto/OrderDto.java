package com.just.anything.dto;

import com.just.anything.domain.Address;
import com.just.anything.domain.Order;
import com.just.anything.domain.OrderItem;
import com.just.anything.domain.OrderStatus;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order o) {
        this.orderId = o.getId();
        this.name = o.getMember().getName();
        this.orderDate = o.getOrderDate();
        this.orderStatus = o.getStatus();
        this.address = o.getDelivery().getAddress();
        this.orderItems = o.getOrderItems().stream()
                .map(item -> new OrderItemDto(item))
                .collect(Collectors.toList());
    }

    @Getter
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem item) {
            itemName = item.getItem().getName();
            orderPrice = item.getOrderPrice();
            count = item.getCount();
        }
    }
}
