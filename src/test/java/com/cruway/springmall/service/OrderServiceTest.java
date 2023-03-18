package com.cruway.springmall.service;

import com.cruway.springmall.domain.Member;
import com.cruway.springmall.domain.Order;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.item.Book;
import com.cruway.springmall.domain.item.Item;
import com.cruway.springmall.domain.status.OrderStatus;
import com.cruway.springmall.exception.NotEnoughStockException;
import com.cruway.springmall.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 商品注文() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("本JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("商品注文しステータスはORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("注文した商品の種類が正しいこと", 1, getOrder.getOrderItems().size());
        assertEquals("注文価格の総額は", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("注文数量分、在庫が減ること", 8, book.getStockQuantity());
    }

    @Test
    public void 商品注文_在庫数量超過() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("本JPA", 10000, 10);

        int orderCount = 11;

        //when
        NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        //then
        assertEquals("在庫数量不足例外が発生する必要がある", "need more stock", ex.getMessage());
    }

    @Test
    public void 注文キャンセル() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("JPA2", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("注文キャンセルの場合CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("注文がキャンセルした商品は在庫が増加", 10, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = Member.builder()
                .userName("会員１")
                .homeAddress(Address.builder()
                        .city("東京")
                        .street("板橋区")
                        .zipcode("123-1231")
                        .build())
                .build();
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.bookBuilder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
        em.persist(book);
        return book;
    }
}