package com.github.fabriciolfj.accountservice.repository;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccountRepository extends CrudRepository<AccountData, String> {

    private static final String ID_ACCOUNT = "ID";
    private static final String DATE_CREATION = "date_creation";
    private static final String CUSTOMER = "customer";

    public AccountRepository() {
        super("account");
    }

    @Override
    protected Map<String, AttributeValue> mapItemIdAttribute(final String id) {
        return Collections.singletonMap(ID_ACCOUNT, AttributeValue.builder().s(id).build());
    }

    @Override
    protected Map<String, AttributeValueUpdate> mapItemSaveAttributes(final AccountData entity) {
        return getItem(entity);
    }

    @Override
    protected Map<String, AttributeValue> mapItemEntityIdAttribute(final AccountData id) {
        return Collections.singletonMap(ID_ACCOUNT, AttributeValue.builder().s(id.getAccountId()).build());
    }

    @Override
    protected AccountData mapAttributesToEntity(final Map<String, AttributeValue> responseAttributes) {
        return buildData(responseAttributes);
    }

    private Map<String, AttributeValueUpdate> getItem(final AccountData accountData) {
        final Map<String, AttributeValueUpdate> attributes = new HashMap<>();
        attributes.put(DATE_CREATION, computeUpdateValueIfNotNull(accountData.getDateCreation()));
        attributes.put(CUSTOMER, computeUpdateValueIfNotNull(accountData.getCustomer()));

        return attributes;
    }

    private AccountData buildData(final Map<String, AttributeValue> map) {
        return AccountData
                .builder()
                .accountId(getAttributeValue(map, ID_ACCOUNT).s())
                .dateCreation(getAttributeValue(map, DATE_CREATION).s())
                .customer(getAttributeValue(map, CUSTOMER).s())
                .build();
    }
}
