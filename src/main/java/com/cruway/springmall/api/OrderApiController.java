package com.cruway.springmall.api;

import com.cruway.springmall.domain.Order;
import com.cruway.springmall.domain.OrderItem;
import com.cruway.springmall.domain.OrderSearch;
import com.cruway.springmall.domain.embeded.Address;
import com.cruway.springmall.domain.status.OrderStatus;
import com.cruway.springmall.repository.OrderRepository;
import com.cruway.springmall.repository.order.query.OrderFlatDto;
import com.cruway.springmall.repository.order.query.OrderItemQueryDto;
import com.cruway.springmall.repository.order.query.OrderQueryDto;
import com.cruway.springmall.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByJpql(OrderSearch.builder().build());
        for (Order order : all) {
            order.getMember().getUserName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2() {
        return orderRepository.findAllByJpql(OrderSearch.builder().build())
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3() {
        return orderRepository.findAllWithItem()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit)
        {
        return orderRepository.findAllWithMemberDelivery(offset, limit)
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> orderV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> orderV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                        )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUserName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {
        // 商品名
        private String itemName;
        // 注文価格
        private int orderPrice;
        // 注文数量
        private int count;
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}