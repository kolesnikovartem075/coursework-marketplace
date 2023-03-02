package com.artem.dto;

import lombok.Value;

@Value
public class CustomerCreateEditDto {

    String firstName;
    String lastName;
    String email;
    String phone;
}
