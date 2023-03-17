package com.cruway.springmall.domain;

import com.cruway.springmall.domain.status.DeliveryStatus;
import com.cruway.springmall.domain.status.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name ="orders")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY , cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 注文時間
    private LocalDateTime orderDate;

    // 注文状態 [ORDER, CANCEL]
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(Long id, Member member, Delivery delivery, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.setMember(member);
        this.setDelivery(delivery);
        this.orderItems = new ArrayList<>();
        this.orderDate = orderDate;
        this.status = status;
    }

    // 関連関係method
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 生成method
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    // ビジニスロジック
    /**
     * 注文キャンセル
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("すでに配送完了になった商品はキャンセルが不可能です。");
        }
        this.status  = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //照会ロジック
    /**
     * 全ての注文価格照会
     * @return
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
