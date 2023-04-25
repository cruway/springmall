package com.cruway.springmall.repository;

import com.cruway.springmall.controller.form.MemberDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@RequiredArgsConstructor
public class MemberTestRepositoryImpl implements MemberTestRepositoryQuery {
    //private final JPAQueryFactory queryFactory;

    private final EntityManager entityManager;
    public List<MemberDto> getList() {
        String sql = "SELECT m.member_id AS id, m.user_name AS userName FROM Member m";

        Query nativeQuery = entityManager.createNativeQuery(sql, "MemberDtoMapping");
        List<MemberDto> memberDtoList = nativeQuery.getResultList();

        return memberDtoList;
    }


}
