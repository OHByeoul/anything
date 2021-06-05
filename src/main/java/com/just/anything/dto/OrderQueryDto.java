package com.just.anything.dto;

import com.just.anything.domain.Address;
import com.just.anything.domain.Order;
import com.just.anything.domain.OrderStatus;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderQueryDto(Long orderId,
                         String name,
                         LocalDateTime orderDate,
                         OrderStatus orderStatus,
                         Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
