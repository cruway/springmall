package com.cruway.springmall.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "会員名は必須です")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
