package jpabook.jpashop.domain.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.entity.order.Orders;
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

    public List<Orders> findAll() {
        return em.createQuery("select o from Orders o", Orders.class).getResultList();
    }

}
