package com.github.fabriciolfj.accountservice.provider;

import com.github.fabriciolfj.accountservice.business.FindAccountProvider;
import com.github.fabriciolfj.accountservice.business.FindAllAccountsProvider;
import com.github.fabriciolfj.accountservice.business.SaveAccountProvider;
import com.github.fabriciolfj.accountservice.entity.AccountEntity;
import com.github.fabriciolfj.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountProvider implements FindAccountProvider, FindAllAccountsProvider, SaveAccountProvider {

    private final AccountRepository repository;

    @Override
    public List<AccountEntity> process(final String dateStart, final String dateEnd, final String customer) {
        final Map<String, String> expressionName = new HashMap<>();
        expressionName.put("#date", "date_creation");
        expressionName.put("#customer", "customer");

        final Map<String, AttributeValue> filter = new HashMap<>();
        filter.put(":dateStart", AttributeValue.builder().s(dateStart).build());
        filter.put(":dateEnd", AttributeValue.builder().s(dateEnd).build());
        filter.put(":customer", AttributeValue.builder().s(customer).build());

        var expression = "#date BETWEEN :dateStart and :dateEnd and #customer = :customer";

        return repository.filter(filter, expression, expressionName)
                .stream()
                .map(AccountConverter::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountEntity process(final String code) {
       return repository.findById(code)
               .map(AccountConverter::toEntity)
               .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public void process(final AccountEntity accountEntity) {
        repository.save(AccountConverter.toData(accountEntity));
    }
}
