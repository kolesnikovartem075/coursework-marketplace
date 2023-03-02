package com.artem.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class OrderCreateEditDto {

    Long customerId;

    Long paymentMethodId;


    String orderStatus;

    Integer orderTotal;

    LocalDate dateCreated;
}