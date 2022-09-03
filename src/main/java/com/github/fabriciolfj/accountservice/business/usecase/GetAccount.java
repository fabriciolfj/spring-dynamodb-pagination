package com.github.fabriciolfj.accountservice.business.usecase;

import com.github.fabriciolfj.accountservice.business.FindAccountProvider;
import com.github.fabriciolfj.accountservice.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccount {

    private final FindAccountProvider provider;

    public AccountEntity execute(final String code) {
        return provider.process(code);
    }
}
