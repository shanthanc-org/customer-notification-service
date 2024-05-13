package org.shanthan.customernotificationservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import static org.shanthan.customernotificationservice.util.CustomerNotificationConstants.*;

@Value
@Builder
public class CustomerProfile {

    @NotEmpty
    @Pattern(regexp = ACC_KEY_REGEXP)
    @Size(min = ACC_KEY_LEN, max = ACC_KEY_LEN)
    String accountKey;

    @NotEmpty
    @Pattern(regexp = ACC_NUM_REGEXP)
    @Size(min = ACC_NUM_LEN, max = ACC_NUM_LEN)
    String accountNum;

    @NotEmpty
    @Pattern(regexp = NAME_REGEXP)
    String firstName;

    @NotEmpty
    @Pattern(regexp = NAME_REGEXP)
    String lastName;

    Integer age;

    @NotEmpty
    @Pattern(regexp = EMAIL_REGEXP)
    String email;

    @NotEmpty
    @Pattern(regexp = PHONE_REGEXP)
    String phoneNumber;

    @NotEmpty
    String city;
}
