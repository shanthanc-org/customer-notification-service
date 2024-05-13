package org.shanthan.customernotificationservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.shanthan.customernotificationservice.model.CustomerProfile;
import org.shanthan.customernotificationservice.model.ProduceSuccessMessage;
import org.shanthan.customernotificationservice.service.CustomerNotificationProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Slf4j
@RequestMapping("/notification")
public class ConsumerNotificationController {

    private final CustomerNotificationProducerService customerNotificationProducerService;

    public ConsumerNotificationController(CustomerNotificationProducerService customerNotificationProducerService) {
        this.customerNotificationProducerService = customerNotificationProducerService;
    }

    @PostMapping("/v1/send/customer/profile")
    public ResponseEntity<ProduceSuccessMessage> sendNotification(@Valid @RequestBody CustomerProfile customerProfile) {
        ProducerRecord<String, CustomerProfile> producerRecord =
                customerNotificationProducerService.sendMessage(customerProfile);
        ProduceSuccessMessage responseBody = ProduceSuccessMessage.builder()
                .accountKey(producerRecord.key())
                .topic(producerRecord.topic())
                .message("New Customer notification sent successfully")
                .build();

        return ResponseEntity.status(CREATED).body(responseBody);
    }
}
