package com.artem.dto;

import lombok.Value;

@Value
public class ShoppingCartItemCreateEditDto {

    Long shoppingCartId;
    Long productCatalogId;
    Integer quantity;
}