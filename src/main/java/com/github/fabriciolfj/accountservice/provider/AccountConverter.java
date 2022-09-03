package com.github.fabriciolfj.accountservice.provider;

import com.github.fabriciolfj.accountservice.entity.AccountEntity;
import com.github.fabriciolfj.accountservice.repository.AccountData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountConverter {

    private AccountConverter() { }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static AccountData toData(final AccountEntity entity) {
        return AccountData.builder()
                .accountId(entity.getCode())
                .dateCreation(entity.getDateCreated().toString())
                .customer(entity.getCustomer())
                .build();
    }

    public static AccountEntity toEntity(final AccountData data) {
        return AccountEntity.builder()
                .customer(data.getCustomer())
                .code(data.getAccountId())
                .dateCreated(LocalDate.parse(data.getDateCreation(), formatter))
                .build();
    }
}
