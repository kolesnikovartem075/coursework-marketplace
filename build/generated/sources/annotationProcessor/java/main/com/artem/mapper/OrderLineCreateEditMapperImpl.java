package com.artem.mapper;

import com.artem.database.entity.OrderLine;
import com.artem.dto.OrderLineCreateEditDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
@Component
public class OrderLineCreateEditMapperImpl implements OrderLineCreateEditMapper {

    @Override
    public OrderLine map(OrderLineCreateEditDto customerOrderCreateEditDto) {
        if ( customerOrderCreateEditDto == null ) {
            return null;
        }

        OrderLine.OrderLineBuilder orderLine = OrderLine.builder();

        orderLine.quantity( customerOrderCreateEditDto.getQuantity() );
        orderLine.price( customerOrderCreateEditDto.getPrice() );

        return orderLine.build();
    }

    @Override
    public OrderLine map(OrderLineCreateEditDto customerOrderCreateEditDto, OrderLine entity) {
        if ( customerOrderCreateEditDto == null ) {
            return entity;
        }

        entity.setQuantity( customerOrderCreateEditDto.getQuantity() );
        entity.setPrice( customerOrderCreateEditDto.getPrice() );

        return entity;
    }
}
