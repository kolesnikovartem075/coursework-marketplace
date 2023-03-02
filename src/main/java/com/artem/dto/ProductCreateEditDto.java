package com.artem.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class ProductCreateEditDto {

    String title;
    String description;
    MultipartFile image;

    Integer quantity;
    Integer price;
}