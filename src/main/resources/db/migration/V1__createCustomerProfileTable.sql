CREATE TABLE customer_profile (
                                  account_key VARCHAR(9) PRIMARY KEY CHECK (LENGTH(account_key) = 9),
                                  account_number VARCHAR(12) NOT NULL UNIQUE CHECK (LENGTH(account_number) = 12),
                                  first_name VARCHAR NOT NULL,
                                  last_name VARCHAR NOT NULL,
                                  age INT NOT NULL,
                                  email VARCHAR NOT NULL UNIQUE,
                                  phone_number VARCHAR NOT NULL UNIQUE,
                                  city VARCHAR NOT NULL
);
