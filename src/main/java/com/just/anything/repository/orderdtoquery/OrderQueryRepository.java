package com.just.anything.repository.orderdtoquery;

import com.just.anything.dto.OrderFlatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findAllQueryDto() {
        return em.createQuery("select new com.just.anything.repository.orderdtoquery.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                " from Order o"+
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class).getResultList();
    }

    public List<OrderFlatDto> findAllByDto_flat(){
        return em.createQuery("select new " +
                " com.just.anything.dto.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d" +
                " join o.orderItems oi" +
                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
