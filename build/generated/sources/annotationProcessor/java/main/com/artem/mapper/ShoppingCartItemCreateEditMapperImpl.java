package com.artem.mapper;

import com.artem.database.entity.ShoppingCartItem;
import com.artem.dto.ShoppingCartItemCreateEditDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
@Component
public class ShoppingCartItemCreateEditMapperImpl implements ShoppingCartItemCreateEditMapper {

    @Override
    public ShoppingCartItem map(ShoppingCartItemCreateEditDto shoppingCartItemDto) {
        if ( shoppingCartItemDto == null ) {
            return null;
        }

        ShoppingCartItem.ShoppingCartItemBuilder shoppingCartItem = ShoppingCartItem.builder();

        shoppingCartItem.quantity( shoppingCartItemDto.getQuantity() );

        return shoppingCartItem.build();
    }

    @Override
    public ShoppingCartItem map(ShoppingCartItemCreateEditDto shoppingCartItemDto, ShoppingCartItem entity) {
        if ( shoppingCartItemDto == null ) {
            return entity;
        }

        entity.setQuantity( shoppingCartItemDto.getQuantity() );

        return entity;
    }
}
