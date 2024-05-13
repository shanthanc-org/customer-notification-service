package org.shanthan.customernotificationservice.util;

public class CustomerNotificationConstants {

    public static final int ACC_KEY_LEN = 9;

    public static final int ACC_NUM_LEN = 12;

    public static final String NAME_REGEXP = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$";

    public static final String EMAIL_REGEXP =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final String PHONE_REGEXP =
            "^(\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}\\.\\d{3}\\.\\d{4}|\\d{10})$";

    public static final String ACC_KEY_REGEXP = "^[1-5]\\d{8}$";
    public static final String ACC_NUM_REGEXP = "^6\\d{11}$";

    public static final String AGE_REGEXP = "^(1[01][0-9]|12[0]|[1-9]?[0-9])$";

    public static final Integer BUFFER_SIZE = 2;


}
