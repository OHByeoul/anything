package com.just.anything;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() throws Exception {
        Member member = new Member();
        member.setUsername("mem1");
        Long save = memberRepository.save(member);
        Member member1 = memberRepository.find(save);

        Assertions.assertThat(member.getId()).isEqualTo(member1.getId());
    }
}