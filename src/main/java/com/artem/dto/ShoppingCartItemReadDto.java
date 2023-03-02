package com.artem.dto;

import lombok.Value;

@Value
public class ShoppingCartItemReadDto {

    Long id;

    ProductReadDto productCatalog;
    Integer quantity;
}