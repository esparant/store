package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.api.dto.OrderDto;
import jpabook.jpashop.api.dto.Result;
import jpabook.jpashop.domain.order.Orders;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.query.OrderQueryRepository;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Orders> ordersV1() {
        return orderService.findOrders(new OrderSearch());
        // 직접 노출 금지 하지마 하지마
    }

    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> ordersV2() {
        List<OrderDto> orders = orderService.findOrders(new OrderSearch())
                .stream()
                .map(OrderDto::new)
                .toList();

        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v3/orders")
    public Result<List<OrderDto>> ordersV3() {
        List<OrderDto> orders = orderService.findAllWithOrderItems()
                .stream()
                .map(OrderDto::new)
                .toList();

        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v3.1/orders")
    public Result<List<OrderDto>> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<OrderDto> orders = orderService.findAllWithMemberDelivery(offset, limit)
                .stream()
                .map(OrderDto::new)
                .toList();

        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v4/orders")
    public Result<List<OrderDto>> ordersV4() {

        List<OrderDto> orders = orderQueryRepository.findOrderQuery();

        return new Result<>(orders.size(), orders);
    }



}
