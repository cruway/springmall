package com.cruway.springmall.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    // 性能的に良いがほぼ差がない、できればfetch joinを使う
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return  em.createQuery(
                "select new com.cruway.springmall.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.userName, o.orderDate, o.status, d.address) " +
                        "from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
