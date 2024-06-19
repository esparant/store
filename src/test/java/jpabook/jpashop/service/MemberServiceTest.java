package jpabook.jpashop.service;


import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.common.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void joinAndFindOne() {
        Member memberA = new Member("hello", new Address());

        Long joinedMemberId = memberService.join(memberA);
        Member foundMember = memberService.findOne(joinedMemberId);

        assertThat(foundMember).isEqualTo(memberA);
    }

    @Test
    void duplicatedJoin() {
        Member memberA = new Member("hello", new Address());
        Member memberB = new Member("hello", new Address());

        memberService.join(memberA);

        assertThatThrownBy(() -> memberService.join(memberB))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void findMembers() {
        Member memberA = new Member("hello", new Address());
        Member memberB = new Member("world", new Address());

        memberService.join(memberA);
        memberService.join(memberB);

        assertThat(memberService.findMembers()).hasSize(2);
    }
}