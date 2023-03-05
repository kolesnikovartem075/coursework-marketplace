package com.artem.mapper;

import com.artem.database.entity.ShoppingCart;
import com.artem.dto.ShoppingCartItemReadDto;
import com.artem.dto.ShoppingCartReadDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ShoppingCartReadMapper implements Mapper<ShoppingCart, ShoppingCartReadDto> {

    private final ShoppingCartItemReadMapper shoppingCartItemReadMapper = Mappers.getMapper(ShoppingCartItemReadMapper.class);

    @Override
    public ShoppingCartReadDto map(ShoppingCart object) {
        List<ShoppingCartItemReadDto> items = getItems(object);
        Integer totalSum = getTotalSum(items);

        return new ShoppingCartReadDto(object.getId(),
                object.getCustomer().getId(),
                totalSum,
                items);
    }

    private List<ShoppingCartItemReadDto> getItems(ShoppingCart object) {
        return Optional.ofNullable(object.getItems())
                .orElseGet(Collections::emptyList).stream()
                .map(shoppingCartItemReadMapper::map)
                .toList();
    }

    private int getTotalSum(List<ShoppingCartItemReadDto> items) {
        return items.stream()
                .map(item -> item.getQuantity() * item.getProductCatalog().getPrice())
                .mapToInt(price -> price)
                .sum();
    }
}