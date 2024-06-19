package jpabook.jpashop.service;


import static org.assertj.core.api.Assertions.*;

import java.util.List;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.common.Member;
import jpabook.jpashop.domain.common.OrderItem;
import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.order.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    @Test
    void order() {
        Address address = new Address("안녕", "대전시", "1557");
        Member member = new Member("hello", address);
        Long memberId = memberService.join(member);

        Item book = new Book("spring jpa", 20000, 100, "박박박", "122A231B");
        Long bookId = itemService.saveItem(book);

        // 멤버가 주문을 가지고 있는가 and OrderItem 의 개수가 한개인가
        Long orderId = orderService.order(memberId, bookId, 20);
        assertThat(member.getOrders()).isNotEmpty();
        assertThat(orderService.findOrder(orderId).getOrderItems()).hasSize(1);

        // 오더의 TotalPrice = 20 * 20000 인가
        assertThat(orderService.findOrder(orderId).getTotalPrice()).isEqualTo(20 * 20000);

        // 오더아이템이 생성되었는가
        List<OrderItem> orderItems = orderService.findOrder(orderId).getOrderItems();
        assertThat(orderItems).isNotEmpty();

        // 오더의 status 가 Order 인가
        assertThat(orderService.findOrder(orderId).getStatus()).isEqualTo(OrderStatus.ORDER);

        // 배송이 등록되엇는가
        Delivery delivery = orderService.findOrder(orderId).getDelivery();
        assertThat(delivery).isNotNull();

        // 책의 재고가 20개 줄어들었는가
        assertThat(book.getStock()).isEqualTo(80);

        // 책의 주문이 취소되고 재고가 다시 원래대로 돌아 왔는가
        orderService.cancelOrder(orderId);
        assertThat(book.getStock()).isEqualTo(100);

        // 재고가 부족할시 예외가 발생하는가
        Album album = new Album("소녀시대", 20000, 10, "소녀시대", "한정판");
        Long albumId = itemService.saveItem(album);
        assertThatThrownBy(() -> orderService.order(memberId, albumId, 11))
                .isInstanceOf(NotEnoughStockException.class);
    }
}