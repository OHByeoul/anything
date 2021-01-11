package com.just.anything.service;

import com.just.anything.domain.Member;
import com.just.anything.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getName());
        if(!members.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
