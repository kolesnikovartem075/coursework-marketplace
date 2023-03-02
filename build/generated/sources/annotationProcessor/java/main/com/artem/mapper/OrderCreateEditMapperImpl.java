package com.artem.mapper;

import com.artem.database.entity.Order;
import com.artem.database.entity.OrderStatus;
import com.artem.dto.OrderCreateEditDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
@Component
public class OrderCreateEditMapperImpl implements OrderCreateEditMapper {

    @Override
    public Order map(OrderCreateEditDto orderCreateEditDto) {
        if ( orderCreateEditDto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        if ( orderCreateEditDto.getOrderStatus() != null ) {
            order.orderStatus( Enum.valueOf( OrderStatus.class, orderCreateEditDto.getOrderStatus() ) );
        }
        order.orderTotal( orderCreateEditDto.getOrderTotal() );
        order.dateCreated( orderCreateEditDto.getDateCreated() );

        return order.build();
    }

    @Override
    public Order map(OrderCreateEditDto orderCreateEditDto, Order entity) {
        if ( orderCreateEditDto == null ) {
            return entity;
        }

        if ( orderCreateEditDto.getOrderStatus() != null ) {
            entity.setOrderStatus( Enum.valueOf( OrderStatus.class, orderCreateEditDto.getOrderStatus() ) );
        }
        else {
            entity.setOrderStatus( null );
        }
        entity.setOrderTotal( orderCreateEditDto.getOrderTotal() );
        entity.setDateCreated( orderCreateEditDto.getDateCreated() );

        return entity;
    }
}
