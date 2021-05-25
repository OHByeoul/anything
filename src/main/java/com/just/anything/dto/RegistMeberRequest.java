package com.just.anything.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistMeberRequest {
    @NotEmpty
    private String name;
}
