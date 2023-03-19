package com.cruway.springmall;

import com.cruway.springmall.domain.Delivery;
import com.cruway.springmall.domain.Member;
import com.cruway.springmall.domain.Order;
import com.cruway.springmall.domain.OrderItem;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 全注文2個
 * * userA
 * * JPA1 BOOK
 * * JPA2 BOOK
 * * userB
 * * SPRING1 BOOK
 * * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "seoul", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(member, Delivery.builder()
                    .address(member.getHomeAddress())
                    .build(), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "jeonju", "2", "2222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Order order = Order.createOrder(member, Delivery.builder()
                    .address(member.getHomeAddress())
                    .build(), orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            return Member.builder()
                    .userName(name)
                    .homeAddress(Address.builder()
                            .city(city)
                            .street(street)
                            .zipcode(zipcode)
                            .build())
                    .build();
        }

        private Book createBook(String name, int price, int stockQuantity) {
            return Book.bookBuilder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .build();
        }
    }
}
