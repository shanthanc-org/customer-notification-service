package org.shanthan.customernotificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.shanthan.customernotificationservice.exception.CustomerNotificationException;
import org.shanthan.customernotificationservice.model.CustomerProfile;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.shanthan.customernotificationservice.util.CustomerNotificationConstants.TOPIC_NAME;

@Service
@Slf4j
public class CustomerNotificationProducerService {

    private final KafkaTemplate<String, CustomerProfile> kafkaTemplate;

    public CustomerNotificationProducerService(KafkaTemplate<String, CustomerProfile> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ProducerRecord<String, CustomerProfile> sendMessage(CustomerProfile customerProfile) {
        log.info("Sending customer profile: {} to topic {} ", customerProfile, TOPIC_NAME);
        CompletableFuture<SendResult<String, CustomerProfile>> resultCompletableFuture =
                kafkaTemplate.send(TOPIC_NAME, customerProfile.getAccountKey(), customerProfile);
        resultCompletableFuture.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Error while sending customer profile: {} ", customerProfile, exception);
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Successfully sent customer profile: {} to topic {} and partition {} ",
                        customerProfile, metadata.topic(), metadata.partition());
            }
        });
        try {
            SendResult<String, CustomerProfile> profileSendResult = resultCompletableFuture.get(10,
                    TimeUnit.SECONDS);

            return profileSendResult.getProducerRecord();

        } catch (Exception e) {
            log.error("Error while sending customer profile: {} -> {} \n", customerProfile, e.getMessage(), e);
            throw new CustomerNotificationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
