package com.ded.BTS.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class B2StorageConfig {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .endpointOverride(URI.create(System.getProperty("B2_ENDPOINT")))
            .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    System.getProperty("B2_KEY_ID"),
                    System.getProperty("B2_APP_KEY")
                )
            ))
            .region(Region.US_EAST_1) // region doesn't matter for B2
            .build();
    }
}