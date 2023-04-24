package com.cruway.springmall.repository;

import com.cruway.springmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// QuerydslPredicateExecutorはjoinができない
// Querydslに依存する
// 実務環境では限界がある
public interface MemberTestRepository extends JpaRepository<Member, Long>, MemberTestRepositoryQuery, QuerydslPredicateExecutor<Member> {

}
