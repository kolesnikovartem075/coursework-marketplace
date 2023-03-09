package com.artem.service;


import com.artem.database.repository.PaymentMethodRepository;
import com.artem.dto.PaymentMethodCreateEditDto;
import com.artem.dto.PaymentMethodReadDto;
import com.artem.mapper.PaymentMethodCreateEditMapper;
import com.artem.mapper.PaymentMethodReadMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentMethodService {

    private final PaymentMethodReadMapper paymentMethodReadMapper = Mappers.getMapper(PaymentMethodReadMapper.class);
    private final PaymentMethodCreateEditMapper paymentMethodCreateEditMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    public Optional<PaymentMethodReadDto> findByCustomer(UserDetails userDetails) {
        return paymentMethodRepository.findByCustomerEmail(userDetails.getUsername())
                .map(paymentMethodReadMapper::map);
    }

    public Optional<PaymentMethodReadDto> findByCustomerId(Long costumerId) {
        return paymentMethodRepository.findByCustomerId(costumerId)
                .map(paymentMethodReadMapper::map);
    }

    @Transactional
    public PaymentMethodReadDto create(PaymentMethodCreateEditDto paymentMethod) {
        return Optional.of(paymentMethod)
                .map(paymentMethodCreateEditMapper::map)
                .map(paymentMethodRepository::save)
                .map(paymentMethodReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PaymentMethodReadDto> update(PaymentMethodCreateEditDto paymentMethod, Long id) {
        return paymentMethodRepository.findById(id)
                .map(entity -> paymentMethodCreateEditMapper.map(paymentMethod, entity))
                .map(paymentMethodRepository::saveAndFlush)
                .map(paymentMethodReadMapper::map);
    }
}
