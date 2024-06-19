package jpabook.jpashop.repository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
