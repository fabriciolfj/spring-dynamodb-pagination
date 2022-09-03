package com.github.fabriciolfj.accountservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamodbConfig {

    @Value("${aws.dynamodb.url}")
    private String url;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        final AwsCredentials awsCredentials = AwsBasicCredentials.create("1", "1");
        final AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider
                .create(awsCredentials);

        return DynamoDbClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of("us-east-1"))
                .endpointOverride(URI.create(url))
                .build();
    }
}
