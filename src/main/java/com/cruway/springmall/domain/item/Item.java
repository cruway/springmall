package com.cruway.springmall.domain.item;

import com.cruway.springmall.domain.Category;
import com.cruway.springmall.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id", nullable = false)
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.setName(name);
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categories = new ArrayList<>();
    }

    // ビジニスロジック
    /**
     * stock増加
     * @param quantitiy
     */
    public void addStock(int quantitiy) {
        this.stockQuantity += quantitiy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * stock増加
     * @param quantitiy
     */
    public void removeStock(int quantitiy) {
        int restStock = this.stockQuantity - quantitiy;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
