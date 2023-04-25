package com.cruway.springmall.repository;

import com.cruway.springmall.controller.form.MemberDto;

import java.util.List;

public interface MemberTestRepositoryQuery {
    List<MemberDto> getList();
}
