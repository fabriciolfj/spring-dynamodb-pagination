package com.github.fabriciolfj.accountservice.business;

import com.github.fabriciolfj.accountservice.entity.AccountEntity;

import java.util.List;

public interface FindAllAccountsProvider {

    List<AccountEntity> process(final String dateStart, final String dateEnd, final String customer);
}
