package com.artem.mapper;

import com.artem.database.entity.ShoppingCartItem;
import com.artem.dto.ShoppingCartItemReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface ShoppingCartItemReadMapper {

    ShoppingCartItemReadDto map(ShoppingCartItem order);
}