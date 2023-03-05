package com.artem.dto;

import com.artem.validation.ItemQuantity;
import lombok.Value;

@Value
@ItemQuantity
public class ShoppingCartItemCreateEditDto {

    Long shoppingCartId;
    Long productCatalogId;
    Integer quantity;
}