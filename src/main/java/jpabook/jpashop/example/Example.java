package jpabook.jpashop.example;

import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.common.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class Example {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ItemService itemService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    protected void init() {
        Address address = new Address("hello", "world", "jpa");
        Member memberA = new Member("spring", address);
        Member memberB = new Member("java", address);
        Book bookB = new Book("JAVA", 50000, 100, "LEE", "15C57");
        Book bookA = new Book("JPA", 10000, 200, "KIM", "2A41B");

        Long memberId = memberService.join(memberA);
        memberService.join(memberB);
        Long itemId = itemService.saveItem(bookA);
        itemService.saveItem(bookB);
        orderService.order(memberId, itemId, 20);
    }

}
