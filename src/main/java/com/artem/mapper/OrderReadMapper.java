package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.dto.OrderReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderReadMapper {

    OrderReadDto map(Order order);
}
