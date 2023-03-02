package com.artem.mapper;

import com.artem.database.entity.ShoppingCart;
import com.artem.dto.ShoppingCartReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface ShoppingCartReadMapper {

    ShoppingCartReadDto map(ShoppingCart order);
}
