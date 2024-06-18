package jpabook.jpashop.domain.service;


import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.entity.common.Member;
import org.assertj.core.api.Assertions;
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
        Member memberA = new Member("hello");
        Long joinedMemberId = memberService.join(memberA);

        Member foundMember = memberService.findOne(joinedMemberId);

        assertThat(foundMember).isEqualTo(memberA);
    }

    @Test
    void duplicatedJoin() {
        Member memberA = new Member("hello");
        Member memberB = new Member("hello");

        memberService.join(memberA);

        assertThatThrownBy(() -> memberService.join(memberB))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void findMembers() {
        Member memberA = new Member("hello");
        Member memberB = new Member("world");

        memberService.join(memberA);
        memberService.join(memberB);

        assertThat(memberService.findMembers()).hasSize(2);
    }
}