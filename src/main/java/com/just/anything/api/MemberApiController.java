package com.just.anything.api;

import com.just.anything.domain.Member;
import com.just.anything.dto.RegistMeberRequest;
import com.just.anything.dto.RegistMemberResponse;
import com.just.anything.dto.UpdateMemberRequest;
import com.just.anything.dto.UpdateMemberResponse;
import com.just.anything.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public RegistMemberResponse registMember(@RequestBody @Valid RegistMeberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new RegistMemberResponse(id);
    }

    @PutMapping("/api/v1/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }
}
