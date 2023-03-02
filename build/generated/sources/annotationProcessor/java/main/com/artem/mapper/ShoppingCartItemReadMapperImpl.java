package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.database.entity.ShoppingCartItem;
import com.artem.dto.ProductReadDto;
import com.artem.dto.ShoppingCartItemReadDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
public class ShoppingCartItemReadMapperImpl implements ShoppingCartItemReadMapper {

    @Override
    public ShoppingCartItemReadDto map(ShoppingCartItem order) {
        if ( order == null ) {
            return null;
        }

        Long id = null;
        ProductReadDto productCatalog = null;
        Integer quantity = null;

        id = order.getId();
        productCatalog = productCatalogToProductReadDto( order.getProductCatalog() );
        quantity = order.getQuantity();

        ShoppingCartItemReadDto shoppingCartItemReadDto = new ShoppingCartItemReadDto( id, productCatalog, quantity );

        return shoppingCartItemReadDto;
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
}
