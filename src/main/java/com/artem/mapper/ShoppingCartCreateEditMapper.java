package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.ShoppingCart;
import com.artem.database.repository.CustomerRepository;
import com.artem.dto.ShoppingCartCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShoppingCartCreateEditMapper implements Mapper<ShoppingCartCreateEditDto, ShoppingCart> {

    private final CustomerRepository customerRepository;

    @Override
    public ShoppingCart map(ShoppingCartCreateEditDto object) {
        ShoppingCart shoppingCart = new ShoppingCart();
        copy(object, shoppingCart);


        return shoppingCart;
    }

    @Override
    public ShoppingCart map(ShoppingCartCreateEditDto fromObject, ShoppingCart toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ShoppingCartCreateEditDto object, ShoppingCart shoppingCart) {
        Customer customer = getCustomer(object);

        shoppingCart.setCustomer(customer);
    }

    private Customer getCustomer(ShoppingCartCreateEditDto shoppingCartCreateEditDto) {
        return customerRepository.findById(shoppingCartCreateEditDto.getCustomerId())
                .orElseThrow();
    }
}
