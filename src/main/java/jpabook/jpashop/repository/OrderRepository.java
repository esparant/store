package jpabook.jpashop.repository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.order.Orders;
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

    public List<Orders> findOrdersWithFetchJoinSimply() {
        return em.createQuery("select o from Orders o "
                        + "join fetch o.member m "
                        + "join fetch o.delivery d"
                , Orders.class).getResultList();
    }

    public List<Orders> findOrdersWithFetchJoinSimply(int offset, int limit) {
        return em.createQuery("select o from Orders o "
                        + "join fetch o.member m "
                        + "join fetch o.delivery d"
                , Orders.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Orders> findOrdersWithFetchJoinOrderItems() {
        return em.createQuery("select distinct o from Orders o "
                        + "join fetch o.member m "
                        + "join fetch o.delivery d " // 일대다 패치 조인에서는 페이징 쓰지 말것 메모리를 써버린다.
                        + "join fetch o.orderItems oi "
                        + "join fetch oi.item i"
                , Orders.class).getResultList();
    }
}