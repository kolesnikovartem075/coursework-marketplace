package com.artem.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PaymentMethodCreateEditDto {

    Long customerId;
    Integer accountNumber;
    LocalDate expiryDate;
}
