package com.artem.dto;

import lombok.Value;

@Value
public class OrderLineReadDto {

    Long id;
    ProductReadDto productCatalog;

    Integer quantity;
    Integer price;
}