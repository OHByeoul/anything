package com.just.anything.api;

import com.just.anything.domain.Member;
import com.just.anything.dto.RegistMeberRequest;
import com.just.anything.dto.RegistMemberResponse;
import com.just.anything.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/member/v1/create")
    public RegistMemberResponse registMember(@RequestBody @Valid RegistMeberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new RegistMemberResponse(id);
    }
}
