package jpabook.jpashop.repository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import java.util.List;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Orders order) {
        em.persist(order);
    }

    public Orders findById(Long id) {
        return em.find(Orders.class, id);
    }

    // 주문 검색
    public List<Orders> searchOrders(OrderSearch orderSearch) {
        String jpql = "select o from Orders o join o.member m";

        if (StringUtils.isNotBlank(orderSearch.getMemberName()) && orderSearch.getOrderStatus() != null) {
            return em.createQuery(jpql + " where o.status = :status and m.name like :memberName", Orders.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setParameter("memberName", "%" + orderSearch.getMemberName() + "%")
                    .getResultList();
        }

        if (StringUtils.isNotBlank(orderSearch.getMemberName())) {
            return em.createQuery(jpql + " where m.name like :memberName", Orders.class)
                    .setParameter("memberName", "%" + orderSearch.getMemberName() + "%")
                    .getResultList();
        }

        if (orderSearch.getOrderStatus() != null) {
            return em.createQuery(jpql + " where o.status = :status", Orders.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .getResultList();
        }

        return em.createQuery(jpql, Orders.class).getResultList();
    }

}
