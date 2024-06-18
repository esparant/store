package jpabook.jpashop.domain.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
            return;
        }
        em.merge(item); // 이미 db 에 존재하는 item 이라면 병합한다.
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
