package com.cruway.springmall.domain.item;

import com.cruway.springmall.domain.Category;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
    @Builder(builderMethodName = "albumBuilder")
    public Album(Long id, String name, int price, int stockQuantity, String artist) {
        super(id, name, price, stockQuantity);
        this.artist = artist;
    }
}
