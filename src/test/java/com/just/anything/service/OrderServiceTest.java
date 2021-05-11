package com.just.anything.service;

import com.just.anything.domain.*;
import com.just.anything.exception.NotEnoughStockException;
import com.just.anything.item.Book;
import com.just.anything.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
        Member member = createMember();

        Book book = createBook("책이름", 10000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문상태 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문 상품종류", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격*수량", 10000*orderCount, getOrder.getTotalPrice());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("m1");
        member.setAddress(new Address("인천","거리","12345"));
        em.persist(member);
        return member;
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("책이름", 10000, 10);

        int orderCount = 9;

        //when
        orderService.order(member.getId(),book.getId(),orderCount);

        //then
        Assert.fail("재고수량 부족예외 발생");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
//        Book book = createBook("책이름", 10000, 10);
//        Delivery delivery = new Delivery();
//        delivery.setStatus(DeliveryStatus.READY);
//        Order order = Order.createOrder(member,delivery,book);

        Book book = createBook("책이름", 10000, 10);

        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        //then
        Assert.assertEquals("주문 수량 다시 증가", 10, book.getStockQuantity());
        Assert.assertEquals("주문 취소상태 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());

    }

}