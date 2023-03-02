package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.ProductCatalog;
import com.artem.database.repository.OrderRepository;
import com.artem.database.repository.ProductRepository;
import com.artem.dto.OrderLineCreateEditDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderLineCreateEditMapper {


    OrderLine map(OrderLineCreateEditDto customerOrderCreateEditDto);

    OrderLine map(OrderLineCreateEditDto customerOrderCreateEditDto, @MappingTarget OrderLine entity);

    @AfterMapping
    default void map(OrderLineCreateEditDto customerOrderCreateEditDto,
                     @MappingTarget OrderLine entity,
                     @Context OrderRepository orderRepository,
                     @Context ProductRepository productRepository) {
        Order order = getCustomerOrder(customerOrderCreateEditDto, orderRepository);
        ProductCatalog productCatalog = getProductCatalog(customerOrderCreateEditDto, productRepository);

        entity.setOrder(order);
        entity.setProductCatalog(productCatalog);
    }

    private static ProductCatalog getProductCatalog(OrderLineCreateEditDto customerOrderCreateEditDto, ProductRepository productRepository) {
        return productRepository.findById(customerOrderCreateEditDto.getProductCatalogId()).orElseThrow();
    }

    private static Order getCustomerOrder(OrderLineCreateEditDto customerOrderCreateEditDto, OrderRepository orderRepository) {
        return orderRepository.findById(customerOrderCreateEditDto.getCustomerOrderId())
                .orElseThrow();
    }
}
