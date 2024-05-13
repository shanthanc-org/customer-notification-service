package org.shanthan.customernotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNotificationRepository extends JpaRepository<CustomerProfileEntity, String> {

    List<CustomerProfileEntity> getCustomerProfileEntitiesByAge(Integer age);
    List<CustomerProfileEntity> getCustomerProfileEntitiesByAgeBefore(Integer age);
    List<CustomerProfileEntity> getCustomerProfileEntitiesByAgeAfter(Integer age);
    List<CustomerProfileEntity> getCustomerProfileEntitiesByAgeBetween(Integer age1, Integer age2);
    List<CustomerProfileEntity> getCustomerProfileEntitiesByCity(String city);

}
