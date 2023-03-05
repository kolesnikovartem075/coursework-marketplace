package com.artem.service;

import com.artem.database.repository.ShoppingCartItemRepository;
import com.artem.dto.ShoppingCartItemCreateEditDto;
import com.artem.dto.ShoppingCartItemReadDto;
import com.artem.mapper.ShoppingCartItemCreateEditMapper;
import com.artem.mapper.ShoppingCartItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartItemReadMapper shoppingCartItemReadMapper = Mappers.getMapper(ShoppingCartItemReadMapper.class);
    private final ShoppingCartItemCreateEditMapper shoppingCartItemCreateEditMapper;

    public List<ShoppingCartItemReadDto> findAll() {
        return shoppingCartItemRepository.findAll().stream()
                .map(shoppingCartItemReadMapper::map)
                .toList();
    }

    public List<ShoppingCartItemReadDto> findAllBy(Long shoppingCartId) {
        return shoppingCartItemRepository.findAllByShoppingCartId(shoppingCartId).stream()
                .map(shoppingCartItemReadMapper::map)
                .toList();
    }

    public Optional<ShoppingCartItemReadDto> findById(Long id) {
        return shoppingCartItemRepository.findById(id)
                .map(shoppingCartItemReadMapper::map);
    }


    @Transactional
    public ShoppingCartItemReadDto create(ShoppingCartItemCreateEditDto customerDto) {
        return Optional.of(customerDto)
                .map(shoppingCartItemCreateEditMapper::map)
                .map(shoppingCartItemRepository::save)
                .map(shoppingCartItemReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ShoppingCartItemReadDto> update(Long id, ShoppingCartItemCreateEditDto shoppingCartDto) {
        return shoppingCartItemRepository.findById(id)
                .map(entity -> shoppingCartItemCreateEditMapper.map(shoppingCartDto, entity))
                .map(shoppingCartItemRepository::saveAndFlush)
                .map(shoppingCartItemReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return shoppingCartItemRepository.findById(id)
                .map(entity -> {
                    shoppingCartItemRepository.delete(entity);
                    shoppingCartItemRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}