package com.cruway.springmall.api;

import com.cruway.springmall.domain.Order;
import com.cruway.springmall.domain.OrderSearch;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.status.OrderStatus;
import com.cruway.springmall.repository.OrderRepository;
import com.cruway.springmall.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByJpql(OrderSearch.builder().build());
        for (Order order : all) {
            order.getMember().getUserName(); // Lazy 強制初期化
            order.getDelivery().getAddress(); // Lazy 強制初期化
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleQueryDto> ordersV2() {
        // ORDER 2個
        // N + 1 -> 1 + 会員 N + 配送 N
        return orderRepository.findAllByJpql(OrderSearch.builder().build())
                .stream()
                .map(SimpleQueryDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleQueryDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(SimpleQueryDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleQueryDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleQueryDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getUserName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}