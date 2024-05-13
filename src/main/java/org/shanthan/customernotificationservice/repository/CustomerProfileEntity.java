package org.shanthan.customernotificationservice.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.shanthan.customernotificationservice.util.CustomerNotificationConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "customer_profile")
public class CustomerProfileEntity {

    @Id
    @Size(min = ACC_KEY_LEN, max = ACC_KEY_LEN)
    @Column(name = "account_key")
    @Pattern(regexp = ACC_KEY_REGEXP)
    private String accountKey;

    @NotBlank
    @Column(name = "account_number", unique = true)
    @Size(min = ACC_NUM_LEN, max = ACC_NUM_LEN)
    @Pattern(regexp = ACC_NUM_REGEXP)
    private String accountNumber;

    @NotBlank
    @Column(name = "first_name")
    @Pattern(regexp = NAME_REGEXP)
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Pattern(regexp = NAME_REGEXP)
    private String lastName;

    @NotBlank
    @Pattern(regexp = AGE_REGEXP)
    private Integer age;

    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP)
    private String email;

    @NotBlank
    @Column(name = "phone_number")
    @Pattern(regexp = PHONE_REGEXP)
    private String phoneNumber;

    @NotBlank
    private String city;
}
