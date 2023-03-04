package com.cruway.springmall.domain.item;

import com.cruway.springmall.domain.Category;
import com.cruway.springmall.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // ビジニスロジック

    /**
     * stock増加
     * @param quantitiy
     */
    public void addStock(int quantitiy) {
        this.stockQuantity += quantitiy;
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
