package com.github.fabriciolfj.accountservice.business;

import com.github.fabriciolfj.accountservice.entity.AccountEntity;

public interface SaveAccountProvider {

    void process(final AccountEntity accountEntity);
}
