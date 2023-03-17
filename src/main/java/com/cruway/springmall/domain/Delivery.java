package com.cruway.springmall.domain;

import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.status.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    // READY, COMP
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    public Delivery(Long id, Order order, Address address, DeliveryStatus status) {
        this.id = id;
        this.setOrder(order);
        this.address = address;
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
