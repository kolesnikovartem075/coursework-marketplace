package com.artem.dto;

import lombok.Value;

@Value
public class OrderLineCreateEditDto {

    Long customerOrderId;

    Long productCatalogId;


    Integer quantity;
    Integer price;
}