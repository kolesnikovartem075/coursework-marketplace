package com.artem.dto;

import com.artem.database.entity.OrderStatus;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class OrderReadDto {

    Long id;
    OrderStatus orderStatus;
    Integer orderTotal;
    List<OrderLineReadDto> orderLines;

    LocalDate dateCreated;
}