package com.artem.mapper;

import com.artem.database.entity.Customer;
import com.artem.database.entity.Order;
import com.artem.database.entity.PaymentMethod;
import com.artem.database.repository.CustomerRepository;
import com.artem.database.repository.PaymentMethodRepository;
import com.artem.dto.OrderCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;


@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public Order map(OrderCreateEditDto orderCreateEditDto) {
        Order order = new Order();

        copy(orderCreateEditDto, order);
        order.setDateCreated(LocalDate.now());

        return order;
    }

    public Order map(OrderCreateEditDto orderCreateEditDto, @MappingTarget Order entity) {
        copy(orderCreateEditDto, entity);
        return entity;
    }


    private void copy(OrderCreateEditDto orderCreateEditDto, Order order) {
        Customer customer = getCustomer(orderCreateEditDto);
        PaymentMethod paymentMethod = getPaymentMethod(orderCreateEditDto);
        order.setCustomer(customer);
        order.setPaymentMethod(paymentMethod);
        order.setOrderStatus(orderCreateEditDto.getOrderStatus());
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
