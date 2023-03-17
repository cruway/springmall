package com.cruway.springmall.domain;

import com.cruway.springmall.domain.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName; // 会員名
    private OrderStatus orderStatus; // 注文状態[ORDER, CANCEL]
}
