package jpabook.jpashop.ex;


import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class MemberExRepositoryTest {

    @Autowired
    MemberExRepository memberExRepository;

    @Test
    @Transactional
    @Commit // roll back 하면 db 에 반영되지 않기 때문에 로그에 쿼리가 찍히지 않으니까 commit 넣든지 헤라
    void saveAndFindTest() {
        MemberEx memberEx = new MemberEx("hello");
        Long saveId = memberExRepository.save(memberEx);

        MemberEx findMemberEx = memberExRepository.findById(saveId);

        assertThat(findMemberEx).isEqualTo(memberEx);
        // transactional 실행되는 곳이 분리되어 있기에 hashcode 와 equals 를 member class 에 구현했다.
    }

}