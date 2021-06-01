package com.just.anything;

import com.just.anything.domain.*;
import com.just.anything.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.assembler.MethodExclusionMBeanInfoAssembler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDatabaseData {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = new Member();
            member.setName("ONE");
            member.setAddress(new Address("인천","123","123"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("book1");
            book1.setPrice(1000);
            book1.setStockQuantity(30);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("book2");
            book2.setPrice(2000);
            book2.setStockQuantity(50);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 2000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 2000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }

}


