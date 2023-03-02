package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.database.entity.ShoppingCart;
import com.artem.database.entity.ShoppingCartItem;
import com.artem.dto.ProductReadDto;
import com.artem.dto.ShoppingCartItemReadDto;
import com.artem.dto.ShoppingCartReadDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T13:27:19+0200",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Private Build)"
)
public class ShoppingCartReadMapperImpl implements ShoppingCartReadMapper {

    @Override
    public ShoppingCartReadDto map(ShoppingCart order) {
        if ( order == null ) {
            return null;
        }

        List<ShoppingCartItemReadDto> items = null;
        Long id = null;

        items = shoppingCartItemListToShoppingCartItemReadDtoList( order.getItems() );
        id = order.getId();

        ShoppingCartReadDto shoppingCartReadDto = new ShoppingCartReadDto( id, items );

        return shoppingCartReadDto;
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

    protected ShoppingCartItemReadDto shoppingCartItemToShoppingCartItemReadDto(ShoppingCartItem shoppingCartItem) {
        if ( shoppingCartItem == null ) {
            return null;
        }

        Long id = null;
        ProductReadDto productCatalog = null;
        Integer quantity = null;

        id = shoppingCartItem.getId();
        productCatalog = productCatalogToProductReadDto( shoppingCartItem.getProductCatalog() );
        quantity = shoppingCartItem.getQuantity();

        ShoppingCartItemReadDto shoppingCartItemReadDto = new ShoppingCartItemReadDto( id, productCatalog, quantity );

        return shoppingCartItemReadDto;
    }

    protected List<ShoppingCartItemReadDto> shoppingCartItemListToShoppingCartItemReadDtoList(List<ShoppingCartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ShoppingCartItemReadDto> list1 = new ArrayList<ShoppingCartItemReadDto>( list.size() );
        for ( ShoppingCartItem shoppingCartItem : list ) {
            list1.add( shoppingCartItemToShoppingCartItemReadDto( shoppingCartItem ) );
        }

        return list1;
    }
}
