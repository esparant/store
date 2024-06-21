package jpabook.jpashop.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OrderItem {

    public OrderItem(Item item, int quantity, int price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;

        item.removeStock(this.quantity);
    }

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    private int quantity; // 주문시 수량
    private int price; // 주문시 가격


    public void cancel() {
        item.addStock(quantity);
    }

    public int getTotalPrice() {
        return quantity * price;
    }
}
