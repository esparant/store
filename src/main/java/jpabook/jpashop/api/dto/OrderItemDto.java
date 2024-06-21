package jpabook.jpashop.api.dto;

import jpabook.jpashop.domain.common.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderItemId;
    private String itemName;
    private int quantity;
    private int price;


    public OrderItemDto(OrderItem orderItem) {
        orderItemId = orderItem.getId();
        itemName = orderItem.getItem().getName();
        quantity = orderItem.getQuantity();
        price = orderItem.getPrice();
    }
}
