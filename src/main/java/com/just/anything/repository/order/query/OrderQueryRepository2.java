package com.just.anything.repository.order.query;

import com.just.anything.repository.orderdtoquery.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository2 {
    private final EntityManager em;

    public List<OrderQueryDto2> findOrderQueryDtos() {
        List<OrderQueryDto2> result = findOrders();

        result.forEach(o->{
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto2> findOrderQueryDtos_optimization() {
        List<OrderQueryDto2> result = findOrders();

        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery("select new com.just.anything.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds).getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new com.just.anything.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = :orderId",OrderItemQueryDto.class)
                .setParameter("orderId",orderId).getResultList();
    }

    private List<OrderQueryDto2> findOrders(){
        return em.createQuery("select new com.just.anything.repository.order.query.OrderQueryDto2(o.id,m.name,o.orderDate,o.status,d.address) from Order o"+
                " join o.member m" +
                " join o.delivery d",OrderQueryDto2.class)
                .getResultList();
    }
}
