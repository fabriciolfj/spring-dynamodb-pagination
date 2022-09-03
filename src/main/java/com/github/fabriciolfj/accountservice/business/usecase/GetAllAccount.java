package com.github.fabriciolfj.accountservice.business.usecase;

import com.github.fabriciolfj.accountservice.business.FindAllAccountsProvider;
import com.github.fabriciolfj.accountservice.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetAllAccount {

    private final FindAllAccountsProvider provider;

    public List<AccountEntity> execute(final String dateStart, final String dateEnd, final String customer) {
        return provider.process(dateStart, dateEnd, customer);
    }
}
