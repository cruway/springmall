package com.cruway.springmall.repository;

import com.cruway.springmall.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cruway.springmall.domain.QMember.member;

@RequiredArgsConstructor
public class MemberTestRepositoryImpl implements MemberTestRepositoryQuery {
    private final JPAQueryFactory queryFactory;

    public List<Member> getList() {
        return queryFactory.selectFrom(member).fetch();
    }
}
