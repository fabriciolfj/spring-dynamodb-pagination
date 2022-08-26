package com.github.fabriciolfj.accountservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CrudRepository<T, I> {

    private final String table;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    public CrudRepository(final String table) {
        this.table = table;
    }

    public T save(T entity) {
        var putItem = UpdateItemRequest.builder()
                .attributeUpdates(mapItemSaveAttributes(entity))
                .key(mapItemEntityIdAttribute(entity))
                .returnValues(ReturnValue.ALL_NEW)
                .tableName(this.table)
                .build();

        return this.mapAttributesToEntity(dynamoDbClient.updateItem(putItem).attributes());
    }

    public List<T> filter(final Map<String, AttributeValue> filter, final String expression, final Map<String, String> expressionName) {
        Map<String, AttributeValue> exclusiveKey = new HashMap<>();
        final List<Map<String, AttributeValue>> items = Collections.emptyList();

        do {
            var result = dynamoDbClient.scan(
                    ScanRequest.builder()
                            .filterExpression(expression)
                            .expressionAttributeNames(expressionName)
                            .expressionAttributeValues(filter)
                            .exclusiveStartKey(exclusiveKey)
                            .limit(100)
                            .tableName(this.table)
                            .build());

            if (result.hasItems()) {
                items.addAll(result.items());
            }

            exclusiveKey = result.lastEvaluatedKey();
        } while (!exclusiveKey.isEmpty());

        return items.stream()
                .map(this::mapAttributesToEntity)
                .collect(Collectors.toList());
    }

    public Optional<T> findById(I id) {
        var requestItem = GetItemRequest.builder()
                .key(mapItemIdAttribute(id))
                .tableName(this.table)
                .build();

        var response = dynamoDbClient.getItem(requestItem).item();

        if (Objects.isNull(response) && !response.isEmpty()) {
            return Optional.of(mapAttributesToEntity(response));
        }

        return Optional.empty();
    }

    protected AttributeValue getAttributeValue(Map<String, AttributeValue> response, String key) {
        var attribute = response.get(key);

        if (Objects.nonNull(attribute)) {
            return attribute;
        }

        return AttributeValue.builder().build();
    }

    protected AttributeValueUpdate computeUpdateValueIfNotNull(final Map<String, AttributeValue> attributeValueMap) {
        return convert(AttributeValue.builder().m(attributeValueMap).build());
    }

    protected AttributeValueUpdate computeUpdateValueIfNotNull(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        return convert(AttributeValue.builder().s(value).build());
    }

    private AttributeValueUpdate convert(final AttributeValue attributeValue) {
        return AttributeValueUpdate.builder()
                .value(attributeValue)
                .build();
    }

    protected abstract Map<String, AttributeValue> mapItemIdAttribute(I id);

    protected abstract Map<String, AttributeValueUpdate> mapItemSaveAttributes(T entity);

    protected abstract Map<String, AttributeValue> mapItemEntityIdAttribute(T id);

    protected abstract T mapAttributesToEntity(Map<String, AttributeValue> responseAttributes);


}
