package org.shanthan.customernotificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.*;

@Component
public class CustomerNotificationException extends RuntimeException {

    private HttpStatus status;

    public CustomerNotificationException() {
        status = INTERNAL_SERVER_ERROR;
    }
    public CustomerNotificationException(String message) {
        super(message);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public CustomerNotificationException(String message, Throwable cause) {
        super(message, cause);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public CustomerNotificationException(HttpStatus status, String message) {
        super(message);
        this.status = INTERNAL_SERVER_ERROR;
    }

    public CustomerNotificationException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = INTERNAL_SERVER_ERROR;
    }

}
