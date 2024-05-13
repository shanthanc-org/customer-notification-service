package org.shanthan.customernotificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.shanthan.customernotificationservice.exception.CustomerNotificationException;
import org.shanthan.customernotificationservice.mapper.CustomerNotificationMapper;
import org.shanthan.customernotificationservice.model.CustomerProfile;
import org.shanthan.customernotificationservice.repository.CustomerNotificationRepository;
import org.shanthan.customernotificationservice.repository.CustomerProfileEntity;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CustomerNotificationProducerService {

    private final CustomerNotificationRepository customerNotificationRepository;

    private final KafkaTemplate<String, CustomerProfile> kafkaTemplate;

    private final String topic;

    private final CustomerNotificationMapper mapper;

    public CustomerNotificationProducerService(CustomerNotificationRepository customerNotificationRepository,
                                               KafkaTemplate<String, CustomerProfile> kafkaTemplate, String topic,
                                               CustomerNotificationMapper mapper) {
        this.customerNotificationRepository = customerNotificationRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.mapper = mapper;
    }

    public ProducerRecord<String, CustomerProfile> sendMessage(CustomerProfile customerProfile) {
        log.info("Sending customer profile: {} to topic {} ", customerProfile, topic);
        CompletableFuture<SendResult<String, CustomerProfile>> resultCompletableFuture =
                kafkaTemplate.send(topic, customerProfile.getAccountKey(), customerProfile);
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

    @KafkaListener(topics = "customer.profiles", groupId = "new-customer")
    public void insertCustomerProfile(CustomerProfile customerProfile) {
        try {
            CustomerProfileEntity entity = mapper.mapToEntity(customerProfile);
            customerNotificationRepository.saveAndFlush(entity);
        } catch (Exception e) {
            log.error("Error while adding customer profile to DB: {} -> {} \n", customerProfile, e.getMessage(), e);
            throw new CustomerNotificationException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
