package com.just.anything.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResult<T> {
    private T data;
}
