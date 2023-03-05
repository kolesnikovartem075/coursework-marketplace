package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.ProductCatalog;
import com.artem.database.repository.OrderRepository;
import com.artem.database.repository.ProductRepository;
import com.artem.dto.OrderLineCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLineCreateEditMapper implements Mapper<OrderLineCreateEditDto, OrderLine> {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderLine map(OrderLineCreateEditDto orderCreateEditDto) {
        OrderLine order = new OrderLine();
        copy(orderCreateEditDto, order);

        return order;
    }

    public OrderLine map(OrderLineCreateEditDto orderCreateEditDto, @MappingTarget OrderLine entity) {
        copy(orderCreateEditDto, entity);
        return entity;
    }


    private void copy(OrderLineCreateEditDto orderLineDto, OrderLine entity) {
        Order customerOrder = getCustomerOrder(orderLineDto);
        ProductCatalog productCatalog = getProductCatalog(orderLineDto);

        entity.setOrder(customerOrder);
        entity.setProductCatalog(productCatalog);
        entity.setPrice(orderLineDto.getPrice());
        entity.setQuantity(orderLineDto.getQuantity());
    }

    private Order getCustomerOrder(OrderLineCreateEditDto customerOrderCreateEditDto) {
        return orderRepository.findById(customerOrderCreateEditDto.getCustomerOrderId())
                .orElseThrow();
    }

    private ProductCatalog getProductCatalog(OrderLineCreateEditDto orderLineDto) {
        return productRepository.findById(orderLineDto.getProductCatalogId())
                .orElseThrow();
    }
}

