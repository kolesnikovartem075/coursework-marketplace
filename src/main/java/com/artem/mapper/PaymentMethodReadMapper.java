package com.artem.mapper;

import com.artem.database.entity.PaymentMethod;
import com.artem.dto.PaymentMethodReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMethodReadMapper {
    PaymentMethodReadDto map(PaymentMethod paymentMethod);
}
