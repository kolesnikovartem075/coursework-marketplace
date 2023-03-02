package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.ShoppingCart;
import com.artem.database.repository.CustomerRepository;
import com.artem.dto.ShoppingCartCreateEditDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoppingCartCreateEditMapper {


    ShoppingCart map(ShoppingCartCreateEditDto shoppingCartCreateEditDto);

    ShoppingCart map(ShoppingCartCreateEditDto shoppingCartCreateEditDto, @MappingTarget ShoppingCart entity);

    @AfterMapping
    default void map(ShoppingCartCreateEditDto shoppingCartCreateEditDto,
                     @MappingTarget ShoppingCart entity,
                     @Context CustomerRepository customerRepository) {
        Customer customer = getCustomer(shoppingCartCreateEditDto, customerRepository);

        entity.setCustomer(customer);
    }

    private static Customer getCustomer(ShoppingCartCreateEditDto shoppingCartCreateEditDto, CustomerRepository customerRepository) {
        return customerRepository.findById(shoppingCartCreateEditDto.getCustomerId()).orElseThrow();
    }
}
