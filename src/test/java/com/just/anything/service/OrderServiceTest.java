package com.just.anything.service;

import com.just.anything.domain.Address;
import com.just.anything.domain.Member;
import com.just.anything.domain.Order;
import com.just.anything.domain.OrderStatus;
import com.just.anything.item.Book;
import com.just.anything.item.Item;
import com.just.anything.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.plaf.metal.MetalMenuBarUI;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setName("m1");
        member.setAddress(new Address("인천","거리","12345"));
        em.persist(member);

        Book book = new Book();
        book.setName("책이름");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문상태 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문 상품종류", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격*수량", 10000*orderCount, getOrder.getTotalPrice());
    }

    @Test
    public void 주문취소() throws Exception{
        //given

        //when


        //then

    }

    @Test
    public void 상품주문재고수량초과() throws Exception{
        //given

        //when


        //then

    }
}