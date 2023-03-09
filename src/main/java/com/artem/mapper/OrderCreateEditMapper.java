package com.artem.mapper;

import com.artem.database.entity.*;
import com.artem.database.repository.CustomerRepository;
import com.artem.database.repository.PaymentMethodRepository;
import com.artem.database.repository.ShoppingCartRepository;
import com.artem.dto.OrderCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public Order map(OrderCreateEditDto orderCreateEditDto) {
        Order order = new Order();

        copy(orderCreateEditDto, order);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setDateCreated(LocalDate.now());

        return order;
    }

    public Order map(OrderCreateEditDto orderCreateEditDto, @MappingTarget Order entity) {
        copy(orderCreateEditDto, entity);
        return entity;
    }


    private void copy(OrderCreateEditDto orderCreateEditDto, Order order) {
        var customer = getCustomer(orderCreateEditDto);
        var paymentMethod = getPaymentMethod(orderCreateEditDto);

        order.setCustomer(customer);
        order.setPaymentMethod(paymentMethod);
        order.setOrderTotal(orderCreateEditDto.getTotal());
    }

    private Customer getCustomer(OrderCreateEditDto orderCreateEditDto) {
        return customerRepository.findById(orderCreateEditDto.getCustomerId())
                .orElseThrow();
    }

    private PaymentMethod getPaymentMethod(OrderCreateEditDto orderCreateEditDto) {
        return paymentMethodRepository.findById(orderCreateEditDto.getPaymentMethodId())
                .orElseThrow();
    }
}
