package jpabook.jpashop.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.Orders;
import lombok.Data;

@Data
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    List<OrderItemDto> orderItemsDtos;
    private int totalPrice;
//    private Result<List<OrderItemDto>> orderItemsResult;

    public OrderDto(Orders orders) {
        orderId = orders.getId();
        name = orders.getMember().getName();
        orderDate = orders.getOrderDate();
        orderStatus = orders.getStatus();
        address = orders.getDelivery().getAddress();
        totalPrice = orders.getTotalPrice();

        orderItemsDtos = orders.getOrderItems()
                .stream()
                .map(OrderItemDto::new)
                .toList();
//        this.orderItemsResult = new Result<>(orderItems.size(), orderItems);
    }

    /*private List<OrderItemDto> getOrderItems(Orders orders) {
        List<OrderItemDto> orderItemsDtos = new ArrayList<>();

        List<OrderItem> orderItems = orders.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto(orderItem);
            orderItemsDtos.add(orderItemDto);
        }
        return orderItemsDtos;*/
}

