package com.cruway.springmall.service;

import com.cruway.springmall.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book item = createBook("test", 0, 1);
        Book book = em.find(Book.class, item.getId());
        System.out.println("result = " + book.getName());

        //TX
        book.setName("test111");
        Book result = em.find(Book.class, item.getId());

        System.out.println("result = " + result.getName());

        //変更監視=dirty checking
        //TX commit
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.bookBuilder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .isbn("test1")
                .author("test2")
                .build();
        em.persist(book);
        return book;
    }
}
