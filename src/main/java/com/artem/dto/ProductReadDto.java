package com.artem.dto;

import lombok.Value;

@Value
public class ProductReadDto {

    Long id;
    String title;
    String description;
    String image;

    Integer quantity;
    Integer price;
}