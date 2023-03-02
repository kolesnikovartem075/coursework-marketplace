package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.dto.CustomerCreateEditDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CustomerCreateEditMapper {
    Customer map(CustomerCreateEditDto customerCreateEditDto);

    Customer map(CustomerCreateEditDto customerCreateEditDto, @MappingTarget Customer entity);
}
