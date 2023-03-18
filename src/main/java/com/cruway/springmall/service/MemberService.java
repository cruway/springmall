package com.cruway.springmall.service;

import com.cruway.springmall.domain.Member;
import com.cruway.springmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 会員登録
      */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 重複している会員検証
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getUserName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("すでに存在している会員です");
        }
    }

    /**
     * 全ての会員照会
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setUserName(name);
    }
}
