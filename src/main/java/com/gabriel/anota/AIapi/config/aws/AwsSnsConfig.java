package com.gabriel.anota.AIapi.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.accessKeySecret}")
    private String secretKey;

    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    @Bean
    public AmazonSNS amazonSNSClient() {
        BasicAWSCredentials basicAWSC = new BasicAWSCredentials(accessKeyId, secretKey);
        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSC))
                .withRegion(region)
                .build();
    }

    @Bean(name = "catalogEventsTopic")
    public Topic catalogTopicArn() {
        return new Topic().withTopicArn(catalogTopicArn);
    }


}
