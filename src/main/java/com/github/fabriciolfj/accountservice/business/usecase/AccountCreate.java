package com.github.fabriciolfj.accountservice.business.usecase;

import com.github.fabriciolfj.accountservice.business.SaveAccountProvider;
import com.github.fabriciolfj.accountservice.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCreate {

    private final SaveAccountProvider provider;

    public void execute(final AccountEntity entity) {
        provider.process(entity);
    }
}
