package com.cruway.springmall.service;

import com.cruway.springmall.domain.Member;
import com.cruway.springmall.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

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
        Member member = Member.builder()
                .userName("kim")
                .build();

        // when
        Long savedId = memberService.join(member);

        // then
        //em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 重複会員例外() throws Exception {
        // given
        Member member1 = Member.builder()
                .userName("kim")
                .build();

        Member member2 = Member.builder()
                .userName("kim")
                .build();

        // when
        memberService.join(member1);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2); // 例外が発生する必要がある
        });

        // then
        assertEquals("すでに存在している会員です", ex.getMessage());
    }
    
    @Test
    public void 全ての会員照会() throws Exception {
        //given

        //when
        List<Member> members = memberService.findMembers();
        System.out.println("members = " + members);
        

        //then
    }
}