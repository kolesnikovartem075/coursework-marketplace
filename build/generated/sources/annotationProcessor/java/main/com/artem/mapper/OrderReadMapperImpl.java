package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderLine;
import com.artem.database.entity.OrderStatus;
import com.artem.database.entity.ProductCatalog;
import com.artem.dto.OrderLineReadDto;
import com.artem.dto.OrderReadDto;
import com.artem.dto.ProductReadDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
public class OrderReadMapperImpl implements OrderReadMapper {

    @Override
    public OrderReadDto map(Order order) {
        if ( order == null ) {
            return null;
        }

        List<OrderLineReadDto> orderLines = null;
        Long id = null;
        OrderStatus orderStatus = null;
        Integer orderTotal = null;
        LocalDate dateCreated = null;

        orderLines = orderLineListToOrderLineReadDtoList( order.getOrderLines() );
        id = order.getId();
        orderStatus = order.getOrderStatus();
        orderTotal = order.getOrderTotal();
        dateCreated = order.getDateCreated();

        OrderReadDto orderReadDto = new OrderReadDto( id, orderStatus, orderTotal, orderLines, dateCreated );

        return orderReadDto;
    }

    protected ProductReadDto productCatalogToProductReadDto(ProductCatalog productCatalog) {
        if ( productCatalog == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String description = null;
        String image = null;
        Integer quantity = null;
        Integer price = null;

        id = productCatalog.getId();
        title = productCatalog.getTitle();
        description = productCatalog.getDescription();
        image = productCatalog.getImage();
        quantity = productCatalog.getQuantity();
        price = productCatalog.getPrice();

        ProductReadDto productReadDto = new ProductReadDto( id, title, description, image, quantity, price );

        return productReadDto;
    }

    protected OrderLineReadDto orderLineToOrderLineReadDto(OrderLine orderLine) {
        if ( orderLine == null ) {
            return null;
        }

        Long id = null;
        ProductReadDto productCatalog = null;
        Integer quantity = null;
        Integer price = null;

        id = orderLine.getId();
        productCatalog = productCatalogToProductReadDto( orderLine.getProductCatalog() );
        quantity = orderLine.getQuantity();
        price = orderLine.getPrice();

        OrderLineReadDto orderLineReadDto = new OrderLineReadDto( id, productCatalog, quantity, price );

        return orderLineReadDto;
    }

    protected List<OrderLineReadDto> orderLineListToOrderLineReadDtoList(List<OrderLine> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderLineReadDto> list1 = new ArrayList<OrderLineReadDto>( list.size() );
        for ( OrderLine orderLine : list ) {
            list1.add( orderLineToOrderLineReadDto( orderLine ) );
        }

        return list1;
    }
}
