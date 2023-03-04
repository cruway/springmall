package com.cruway.springmall.domain;

import com.cruway.springmall.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 注文価格
    private int orderPrice;

    // 注文数量
    private int count;

    // 生成method
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

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
