package com.cruway.springmall.domain;

import com.cruway.springmall.domain.status.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    // READY, COMP
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
