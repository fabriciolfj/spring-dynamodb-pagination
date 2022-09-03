package com.github.fabriciolfj.accountservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountResponse {

    private String code;
    private String customer;
    private String date;
}
