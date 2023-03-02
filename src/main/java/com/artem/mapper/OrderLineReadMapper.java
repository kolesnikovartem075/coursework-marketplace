package com.artem.mapper;

import com.artem.database.entity.OrderLine;
import com.artem.dto.OrderLineReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderLineReadMapper {

    OrderLineReadDto map(OrderLine order);
}