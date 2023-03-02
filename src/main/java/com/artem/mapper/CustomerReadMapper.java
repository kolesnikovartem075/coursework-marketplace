package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.dto.CustomerReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerReadMapper {
    CustomerReadDto map(Customer customer);
}
