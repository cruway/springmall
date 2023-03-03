package com.cruway.springmall.service;

import com.cruway.springmall.domain.Member;
import com.cruway.springmall.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    // @Rollback(false)
    public void 会員登録() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        //em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 重複会員例外() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2); // 例外が発生する必要がある
        });

        // then
        assertEquals("すでに存在している会員です", ex.getMessage());
    }
}