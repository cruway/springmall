package com.cruway.springmall.domain.item;

import com.cruway.springmall.domain.Category;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;
    @Builder(builderMethodName = "bookBuilder")
    public Book(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        super(id, name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}