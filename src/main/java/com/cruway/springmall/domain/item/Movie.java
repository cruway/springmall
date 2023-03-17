package com.cruway.springmall.domain.item;

import com.cruway.springmall.domain.Category;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;

    @Builder(builderMethodName = "movieBuilder")
    public Movie(Long id, String name, int price, int stockQuantity, String director, String actor) {
        super(id, name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }
}
