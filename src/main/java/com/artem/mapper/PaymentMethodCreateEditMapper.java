package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.PaymentMethod;
import com.artem.database.entity.ProductCatalog;
import com.artem.database.repository.OrderRepository;
import com.artem.database.repository.ProductRepository;
import com.artem.dto.OrderLineCreateEditDto;
import com.artem.dto.PaymentMethodCreateEditDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMethodCreateEditMapper {


    PaymentMethod map(PaymentMethodCreateEditDto paymentMethodCreateEditDto);

    PaymentMethod map(PaymentMethodCreateEditDto paymentMethodCreateEditDto, @MappingTarget PaymentMethod entity);
}
