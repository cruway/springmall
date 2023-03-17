package com.cruway.springmall.domain;

import com.cruway.springmall.domain.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id", nullable = false)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> child;
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = " category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;
    @Builder
    public Category(Long id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.child = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    // 関連関係method
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.getChild().add(this);
    }
}
