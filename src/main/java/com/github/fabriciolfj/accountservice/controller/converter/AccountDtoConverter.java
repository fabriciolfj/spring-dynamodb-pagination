package com.github.fabriciolfj.accountservice.controller.converter;

import com.github.fabriciolfj.accountservice.controller.dto.AccountRequest;
import com.github.fabriciolfj.accountservice.controller.dto.AccountResponse;
import com.github.fabriciolfj.accountservice.entity.AccountEntity;

import java.time.LocalDate;
import java.util.UUID;

public class AccountDtoConverter {

    private AccountDtoConverter() { }

    public static AccountEntity toEntity(final AccountRequest request) {
        return AccountEntity.builder()
                .dateCreated(LocalDate.now())
                .code(UUID.randomUUID().toString())
                .customer(request.getCustomer())
                .build();
    }

    public static AccountResponse toResponse(final AccountEntity entity) {
        return AccountResponse
                .builder()
                .date(entity.getDateCreated().toString())
                .code(entity.getCode())
                .customer(entity.getCustomer())
                .build();
    }
}
