package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.common.Member;
import jpabook.jpashop.domain.common.OrderItem;
import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Orders;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int quantity) {
        Member member = memberRepository.findById(memberId);

        Item item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery(member.getAddress());

        OrderItem orderItem = new OrderItem(item, quantity, item.getPrice());

        Orders order = new Orders(member, delivery, orderItem);

        // cascade 에 의해 딜리버리, 오더 아이템은 자동으로 생성된다.
        orderRepository.save(order);

        return order.getId();
    }
    // 취소
    @Transactional
    public void cancelOrder(Long itemId) {
        Orders order = orderRepository.findById(itemId);
        order.cancel();
    }

    // 검색
    public Orders findOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Orders> findOrders(OrderSearch orderSearch) {
        return orderRepository.searchOrders(orderSearch);

    }
}
