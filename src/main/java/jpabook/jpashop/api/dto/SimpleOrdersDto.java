package jpabook.jpashop.api.dto;

import java.time.LocalDateTime;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.Orders;
import lombok.Data;

@Data
public class SimpleOrdersDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrdersDto(Orders orders) {
        orderId = orders.getId();
        name = orders.getMember().getName();
        orderDate = orders.getOrderDate();
        orderStatus = orders.getStatus();
        address = orders.getDelivery().getAddress();
    }
}
