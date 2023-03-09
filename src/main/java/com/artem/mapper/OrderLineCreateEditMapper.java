package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.ProductCatalog;
import com.artem.database.repository.OrderRepository;
import com.artem.database.repository.ProductRepository;
import com.artem.dto.OrderLineCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLineCreateEditMapper implements Mapper<OrderLineCreateEditDto, OrderLine> {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderLine map(OrderLineCreateEditDto object) {
        OrderLine order = new OrderLine();
        copy(object, order);

        return order;
    }

    @Override
    public OrderLine map(OrderLineCreateEditDto fromObject, OrderLine toObject) {
        copy(fromObject, toObject);
        return toObject;
    }


    private void copy(OrderLineCreateEditDto orderLine, OrderLine entity) {
        var productCatalog = getProductCatalog(orderLine);
        var order = getOrder(orderLine);

        entity.setProductCatalog(productCatalog);
        entity.setOrder(order);
        entity.setPrice(orderLine.getPrice());
        entity.setQuantity(orderLine.getQuantity());
    }

    private Order getOrder(OrderLineCreateEditDto orderLine) {
        return orderRepository.findById(orderLine.getCustomerOrderId())
                .orElseThrow();
    }

    private ProductCatalog getProductCatalog(OrderLineCreateEditDto orderLine) {
        return productRepository.findById(orderLine.getProductCatalogId())
                .orElseThrow();
    }
}

