package com.cruway.springmall.domain;

import com.cruway.springmall.domain.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id", nullable = false)
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int orderPrice;
    private int count;

    @Builder
    public OrderItem(Long id, Order order, Item item, int orderPrice, int count) {
        this.id = id;
        this.setOrder(order);
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
        //order.addOrderItem(this);
    }

    // 生成method
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .orderPrice(orderPrice)
                .count(count)
                .build();

        item.removeStock(count);
        return orderItem;
    }

    // ビジニスロジック
    /**
     * 注文キャンセル
     */
    public void cancel() {
        getItem().addStock(count);
    }

    // 照会ロジック

    /**
     * 注文商品全体価格照会
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
