package com.cruway.springmall.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
