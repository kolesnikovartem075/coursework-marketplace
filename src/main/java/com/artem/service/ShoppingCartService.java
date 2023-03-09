package com.artem.service;

import com.artem.database.repository.ShoppingCartRepository;
import com.artem.dto.*;
import com.artem.mapper.ShoppingCartCreateEditMapper;
import com.artem.mapper.ShoppingCartReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class ShoppingCartService {

    public static final int QUANTITY = 1;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemService shoppingCartItemService;
    private final CustomerService customerService;
    private final ShoppingCartReadMapper shoppingCartReadMapper;
    private final ShoppingCartCreateEditMapper shoppingCartCreateEditMapper;

    public List<ShoppingCartReadDto> findAll() {
        return shoppingCartRepository.findAll().stream()
                .map(shoppingCartReadMapper::map)
                .toList();
    }

    public Optional<ShoppingCartReadDto> findById(Long id) {
        return shoppingCartRepository.findById(id)
                .map(shoppingCartReadMapper::map);
    }

    public Optional<ShoppingCartReadDto> findBy(UserDetails userDetails) {
        return customerService.findByEmail(userDetails.getUsername())
                .map(CustomerReadDto::getId)
                .flatMap(this::findBy);
    }

    public Optional<ShoppingCartReadDto> findBy(Long customerId) {
        return shoppingCartRepository.findByCustomerId(customerId)
                .map(shoppingCartReadMapper::map);
    }

    @Transactional
    public ShoppingCartReadDto create(ShoppingCartCreateEditDto customerDto) {
        return Optional.of(customerDto)
                .map(shoppingCartCreateEditMapper::map)
                .map(shoppingCartRepository::save)
                .map(shoppingCartReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ShoppingCartReadDto> update(UserDetails userDetails, ShoppingCartCreateEditDto customerDto) {
        return shoppingCartRepository.findByCustomerEmail(userDetails.getUsername())
                .map(entity -> shoppingCartCreateEditMapper.map(customerDto, entity))
                .map(shoppingCartRepository::saveAndFlush)
                .map(shoppingCartReadMapper::map);
    }

    @Transactional
    public boolean delete(UserDetails userDetails) {
        return shoppingCartRepository.findByCustomerEmail(userDetails.getUsername())
                .map(entity -> {
                    shoppingCartRepository.delete(entity);
                    shoppingCartRepository.flush();
                    return true;
                })
                .orElse(false);
    }
    @Transactional
    public boolean delete(Long id) {
        return shoppingCartRepository.findById(id)
                .map(entity -> {
                    shoppingCartRepository.delete(entity);
                    shoppingCartRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void putItem(Long productId, String email) {
        Long customerId = getCustomerId(email);
        ShoppingCartReadDto shoppingCart = getShoppingCart(customerId);
        if (!isPresent(productId, shoppingCart)) {
            log.error("Trying to put the same item into shopping cart");
            return;
        }

        ShoppingCartItemCreateEditDto shoppingCartItemCreateEditDto = new ShoppingCartItemCreateEditDto(shoppingCart.getId(), productId, QUANTITY);
        shoppingCartItemService.create(shoppingCartItemCreateEditDto);
    }

    private boolean isPresent(Long productId, ShoppingCartReadDto shoppingCart) {
        return shoppingCart.getItems().stream()
                .map(ShoppingCartItemReadDto::getProductCatalog)
                .map(ProductReadDto::getId)
                .filter(id -> Objects.equals(id, productId))
                .toList()
                .isEmpty();
    }

    private ShoppingCartReadDto getShoppingCart(Long customerId) {
        return findBy(customerId)
                .orElseGet(() -> create(new ShoppingCartCreateEditDto(customerId)));
    }

    private Long getCustomerId(String email) {
        return customerService.findByEmail(email)
                .map(CustomerReadDto::getId)
                .orElseThrow();
    }
}