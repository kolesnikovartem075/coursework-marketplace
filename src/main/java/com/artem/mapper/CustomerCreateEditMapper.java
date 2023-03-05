package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.Role;
import com.artem.dto.CustomerCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerCreateEditMapper implements Mapper<CustomerCreateEditDto, Customer> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer map(CustomerCreateEditDto object) {
        Customer customer = new Customer();
        copy(object, customer);

        return customer;
    }

    @Override
    public Customer map(CustomerCreateEditDto fromObject, Customer toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CustomerCreateEditDto object, Customer customer) {
        customer.setFirstName(object.getFirstName());
        customer.setLastName(object.getLastName());
        customer.setPhone(object.getPhone());
        customer.setRole(Role.CUSTOMER);
        customer.setEmail(object.getEmail());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(customer::setPassword);
    }
}