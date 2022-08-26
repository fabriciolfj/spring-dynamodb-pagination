package com.github.fabriciolfj.accountservice.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountData {

    private String accountId;
    private String dateCreation;
}
