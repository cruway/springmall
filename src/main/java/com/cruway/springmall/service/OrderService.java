package com.cruway.springmall.service;

import com.cruway.springmall.domain.*;
import com.cruway.springmall.domain.item.Item;
import com.cruway.springmall.repository.ItemRepository;
import com.cruway.springmall.repository.MemberRepository;
import com.cruway.springmall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 注文
      */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // entity照会
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.fineOne(itemId);

        // 配送情報生成
        Delivery delivery = Delivery.builder()
                .address(member.getHomeAddress())
                .build();

        // 注文商品生成
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 注文生成
        Order order = Order.createOrder(member, delivery, orderItem);

        // 注文保存
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * キャンセル
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 注文entity照会
        Order order = orderRepository.findOne(orderId);
        // 注文キャンセル
        order.cancel();
    }

    /**
     * 検索
     * @param orderSearch
     * @return
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByJpql(orderSearch);
    }
}
