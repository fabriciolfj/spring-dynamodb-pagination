package com.github.fabriciolfj.accountservice.business;

import com.github.fabriciolfj.accountservice.entity.AccountEntity;

public interface FindAccountProvider {

    AccountEntity process(final String code);
}
