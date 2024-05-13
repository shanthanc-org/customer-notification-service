package org.shanthan.customernotificationservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.shanthan.customernotificationservice.model.CustomerProfile;
import org.shanthan.customernotificationservice.repository.CustomerProfileEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerNotificationMapper {

    public CustomerProfileEntity mapToEntity(CustomerProfile customerProfile) {
        return CustomerProfileEntity.builder()
                .accountKey(customerProfile.getAccountKey())
                .accountNumber(customerProfile.getAccountNum())
                .firstName(customerProfile.getFirstName())
                .lastName(customerProfile.getLastName())
                .email(customerProfile.getEmail())
                .age(customerProfile.getAge())
                .city(customerProfile.getCity())
                .build();
    }
}
