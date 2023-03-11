package com.artem.service;

import com.artem.database.repository.OrderRepository;
import com.artem.dto.*;
import com.artem.mapper.CustomerReadMapper;
import com.artem.mapper.OrderCreateEditMapper;
import com.artem.mapper.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper = Mappers.getMapper(OrderReadMapper.class);
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final ProductService productService;
    private final CustomerService customerService;
    private final ShoppingCartService shoppingCartService;
    private final OrderLineService orderLineService;


    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAllByCustomer(UserDetails userDetails) {
        return orderRepository.findByCustomerEmail(userDetails.getUsername()).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto createOrder(OrderCreateEditDto orderDto) {
        var orderReadDto = create(orderDto);
        var customer = getCustomer(orderDto);
        var shoppingCart = getShoppingCart(customer);

        var orderLines = createOrderLines(orderReadDto, shoppingCart);
        orderLines.forEach(this::updateQuantity);
        deleteShoppingCart(customer);

        return orderReadDto;
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    private void updateQuantity(OrderLineReadDto orderLine) {
        productService.subtractQuantity(orderLine.getProductCatalog().getId(), orderLine.getQuantity());
    }

    private CustomerReadDto getCustomer(OrderCreateEditDto orderDto) {
        return customerService.findById(orderDto.getCustomerId()).orElseThrow();
    }

    private void deleteShoppingCart(CustomerReadDto customer) {
        Optional.of(getShoppingCart(customer))
                .map(ShoppingCartReadDto::getId)
                .map(shoppingCartService::delete)
                .orElseThrow();
    }

    private ShoppingCartReadDto getShoppingCart(CustomerReadDto customer) {
        return shoppingCartService.findBy(customer.getId()).orElseThrow();
    }

    private List<OrderLineReadDto> createOrderLines(OrderReadDto orderDto, ShoppingCartReadDto shoppingCart) {
        return shoppingCart.getItems().stream()
                .map(it -> new OrderLineCreateEditDto(orderDto.getId(), it.getProductCatalog().getId(), it.getQuantity(), it.getProductCatalog().getPrice()))
                .map(orderLineService::create)
                .toList();
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto order) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(order, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }
}
