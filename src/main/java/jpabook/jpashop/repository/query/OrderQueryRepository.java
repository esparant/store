package jpabook.jpashop.repository.query;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.api.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderDto> findOrderQuery() {


        return em.createQuery("select new jpabook.jpashop.api.dto.OrderDto(o) "
                + "from Orders o "
                + "join fetch o.member m "
                + "join fetch o.delivery d ", OrderDto.class)
                .getResultList();
    }



}
