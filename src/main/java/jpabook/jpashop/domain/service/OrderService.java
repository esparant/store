package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.entity.common.Member;
import jpabook.jpashop.domain.entity.common.OrderItem;
import jpabook.jpashop.domain.entity.delivery.Delivery;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.domain.entity.order.Orders;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
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
}
