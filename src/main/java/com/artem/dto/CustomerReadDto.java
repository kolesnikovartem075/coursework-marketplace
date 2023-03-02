package com.artem.dto;

import lombok.Value;

@Value
public class CustomerReadDto {

    Long id;
    String firstName;
    String lastName;
    String email;
    String phone;
}