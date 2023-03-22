package com.cruway.springmall.api;

import com.cruway.springmall.domain.Order;
import com.cruway.springmall.domain.OrderSearch;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.status.OrderStatus;
import com.cruway.springmall.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<SimpleOrderDto> ordersV2() {
        return orderRepository.findAllByJpql(OrderSearch.builder().build()).stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUserName(); // Lazy 強制初期化
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy 強制初期化
        }
    }
}