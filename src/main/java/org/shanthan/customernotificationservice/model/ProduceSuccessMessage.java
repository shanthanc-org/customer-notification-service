package org.shanthan.customernotificationservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProduceSuccessMessage {

    String accountKey;
    String topic;
    String message;
}
