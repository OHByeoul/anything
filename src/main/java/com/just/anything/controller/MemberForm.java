package com.just.anything.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "이름은 필수 입력 사항입니다.")
    private String name;
    private String city;
    private String street;
    @NotEmpty(message = "우편번호는 필수 입력 사항입니다.")
    private String zipcode;
}
