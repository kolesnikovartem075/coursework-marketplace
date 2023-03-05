package com.artem.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CustomerCreateEditDto {

    String firstName;
    String lastName;
    String email;
    String phone;
    String rawPassword;
}
