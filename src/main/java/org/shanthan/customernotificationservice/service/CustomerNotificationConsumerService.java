package org.shanthan.customernotificationservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.shanthan.customernotificationservice.exception.CustomerNotificationException;
import org.shanthan.customernotificationservice.mapper.CustomerNotificationMapper;
import org.shanthan.customernotificationservice.model.CustomerProfile;
import org.shanthan.customernotificationservice.repository.CustomerNotificationRepository;
import org.shanthan.customernotificationservice.repository.CustomerProfileEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.shanthan.customernotificationservice.util.CustomerNotificationConstants.BUFFER_SIZE;
import static org.springframework.http.HttpStatus.*;

@Service
@Slf4j
public class CustomerNotificationConsumerService {

    private final CustomerNotificationRepository customerNotificationRepository;

    private final CustomerNotificationMapper mapper;

    private final List<CustomerProfileEntity> customerProfileEntityBuffer;


    public CustomerNotificationConsumerService(CustomerNotificationRepository customerNotificationRepository,
                                               CustomerNotificationMapper mapper,
                                               List<CustomerProfileEntity> customerProfileEntityBuffer) {
        this.customerNotificationRepository = customerNotificationRepository;
        this.mapper = mapper;
        this.customerProfileEntityBuffer = customerProfileEntityBuffer;
    }

    @Transactional
    @KafkaListener(topics = "customer.profiles", groupId = "new-customer")
    public void insertCustomerProfiles(CustomerProfile customerProfile) throws CustomerNotificationException {
        try {
            customerProfileEntityBuffer.add(mapper.mapToEntity(customerProfile));
            if (customerProfileEntityBuffer.size() >= BUFFER_SIZE) {
                customerNotificationRepository.saveAllAndFlush(customerProfileEntityBuffer);
            }
        } catch (Exception e) {
            log.error("Error while inserting customer profiles into DB -> {} \n", e.getMessage(), e);
            throw new CustomerNotificationException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
