package com.artem.mapper;

import com.artem.database.entity.ShoppingCart;
import com.artem.dto.ShoppingCartCreateEditDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
@Component
public class ShoppingCartCreateEditMapperImpl implements ShoppingCartCreateEditMapper {

    @Override
    public ShoppingCart map(ShoppingCartCreateEditDto shoppingCartCreateEditDto) {
        if ( shoppingCartCreateEditDto == null ) {
            return null;
        }

        ShoppingCart.ShoppingCartBuilder shoppingCart = ShoppingCart.builder();

        return shoppingCart.build();
    }

    @Override
    public ShoppingCart map(ShoppingCartCreateEditDto shoppingCartCreateEditDto, ShoppingCart entity) {
        if ( shoppingCartCreateEditDto == null ) {
            return entity;
        }

        return entity;
    }
}
