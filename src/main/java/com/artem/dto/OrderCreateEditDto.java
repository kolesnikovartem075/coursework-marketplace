package com.artem.dto;

import com.artem.database.entity.OrderStatus;
import lombok.Value;

import java.time.LocalDate;

@Value
public class OrderCreateEditDto {

    Long customerId;

    Long paymentMethodId;


    OrderStatus orderStatus;

    Integer orderTotal;

    LocalDate dateCreated;
}