package jpabook.jpashop.ex;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public Long save(MemberEx memberEx) {
        em.persist(memberEx);
        return memberEx.getId();
    }

    public MemberEx findById(Long id) {
        return em.find(MemberEx.class, id);
    }

}
