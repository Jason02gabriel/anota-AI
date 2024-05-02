package com.gabriel.anota.AIapi.service.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    @Autowired
    AmazonSNS amazonSNS;

    Topic catalogTopic;

    public AwsSnsService(@Qualifier("catalogEventsTopic") Topic catalogTopic) {
        this.catalogTopic = catalogTopic;
    }

    public void publishMessage(MessageDTO message) {
        amazonSNS.publish(catalogTopic.getTopicArn(), message.toString());
    }

}
