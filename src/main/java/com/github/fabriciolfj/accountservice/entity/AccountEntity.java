package com.github.fabriciolfj.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    private String code;
    private String customer;
    private LocalDate dateCreated;
}
