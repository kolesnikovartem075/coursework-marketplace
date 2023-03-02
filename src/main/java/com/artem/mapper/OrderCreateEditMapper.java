package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.PaymentMethod;
import com.artem.database.repository.CustomerRepository;
import com.artem.database.repository.PaymentMethodRepository;
import com.artem.dto.OrderCreateEditDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderCreateEditMapper {

    Order map(OrderCreateEditDto orderCreateEditDto);

    Order map(OrderCreateEditDto orderCreateEditDto, @MappingTarget Order entity);

    @AfterMapping
    default void map(OrderCreateEditDto orderCreateEditDto,
                     @MappingTarget Order entity,
                     @Context CustomerRepository customerRepository,
                     @Context PaymentMethodRepository paymentMethodRepository) {
        PaymentMethod paymentMethod = getPaymentMethod(orderCreateEditDto, paymentMethodRepository);
        Customer customer = getCustomer(orderCreateEditDto, customerRepository);

        entity.setPaymentMethod(paymentMethod);
        entity.setCustomer(customer);
    }

    private static Customer getCustomer(OrderCreateEditDto orderCreateEditDto, CustomerRepository customerRepository) {
        return customerRepository.findById(orderCreateEditDto.getCustomerId())
                .orElseThrow();
    }

    private static PaymentMethod getPaymentMethod(OrderCreateEditDto orderCreateEditDto, PaymentMethodRepository paymentMethodRepository) {
        return paymentMethodRepository.findById(orderCreateEditDto.getPaymentMethodId())
                .orElseThrow();
    }
}
