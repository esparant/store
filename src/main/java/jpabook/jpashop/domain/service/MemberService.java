package jpabook.jpashop.domain.service;

import java.util.List;
import jpabook.jpashop.domain.entity.common.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // readOnly 를 넣어주면 스프링이 읽기에 최적화를 해준다.
public class MemberService {

    // 트랜잭셔널은 사용자가 직접 접근하는 서비스 계층에 거는것이 좋다. 레포지토리는 오직 데이터와 관련된 로직이 들어있어야 하는 편이 좋기 때문에.

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // 얘가 우선순위 더 놓음 더 자세하기 떄문에
    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id);
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 기능 메서드
    private void validateDuplicatedMember(Member member) {
        if (memberRepository.findByName(member.getName()) != null) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }
}
