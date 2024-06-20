package jpabook.jpashop.api;

import jakarta.validation.Valid;
import java.util.List;
import jpabook.jpashop.api.request.CreateMemberRequest;
import jpabook.jpashop.api.request.UpdateMemberRequest;
import jpabook.jpashop.api.response.CreateMemberResponse;
import jpabook.jpashop.api.response.UpdateMemberResponse;
import jpabook.jpashop.domain.common.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new CreateMemberResponse(null);
        }

        memberService.join(member);
        return new CreateMemberResponse(member.getId());
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }

        Member member = new Member();
        member.setName(request.getName());
        memberService.join(member);

        return new CreateMemberResponse(member.getId());
    }

    @PutMapping("/api/v2/members/{memberId}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("memberId") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }

        Member updateMember = new Member();
        updateMember.setName(request.getName());

        Member member = memberService.update(id, updateMember);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> getAllMembersV1() {

        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> getAllMembersV2() {

        List<MemberDto> members = memberService.findMembers()
                .stream()
                .map(m -> new MemberDto(m.getName()))
                .toList();

        return new Result<>(members.size(), members);
    }


}
