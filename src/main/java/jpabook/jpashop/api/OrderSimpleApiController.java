package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.api.dto.Result;
import jpabook.jpashop.api.dto.SimpleOrdersDto;
import jpabook.jpashop.domain.order.Orders;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ManyToOne, OneToOne 성능최적화
 * <p>
 *
 * @Order
 * @Order -> Member
 * @Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Orders> simpleOrdersV1() {
        return orderService.findOrders(new OrderSearch());
        // entity 를 직접 노출시키지 마라
    }

    @GetMapping("/api/v2/simple-orders")
    public Result<List<SimpleOrdersDto>> simpleOrdersV2() {
        log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        List<SimpleOrdersDto> orders = orderService.findOrders(new OrderSearch())
                .stream()
                .map(SimpleOrdersDto::new)
                .toList();
        // 쿼리 과다 호출 n + 1
        log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result<List<SimpleOrdersDto>> simpleOrdersV3() {
        log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        List<SimpleOrdersDto> orders = orderService.findAllWithMemberDelivery()
                .stream()
                .map(SimpleOrdersDto::new)
                .toList();
        log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        return new Result<>(orders.size(), orders);
    }
}
