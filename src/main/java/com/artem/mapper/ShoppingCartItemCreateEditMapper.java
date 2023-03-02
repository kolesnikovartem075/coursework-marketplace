package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.database.entity.ShoppingCart;
import com.artem.database.entity.ShoppingCartItem;
import com.artem.database.repository.ProductRepository;
import com.artem.database.repository.ShoppingCartRepository;
import com.artem.dto.ShoppingCartItemCreateEditDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemCreateEditMapper {


    ShoppingCartItem map(ShoppingCartItemCreateEditDto shoppingCartItemDto);

    ShoppingCartItem map(ShoppingCartItemCreateEditDto shoppingCartItemDto, @MappingTarget ShoppingCartItem entity);

    @AfterMapping
    default void map(ShoppingCartItemCreateEditDto shoppingCartItemDto,
                     @MappingTarget ShoppingCartItem entity,
                     @Context ShoppingCartRepository shoppingCartRepository,
                     @Context ProductRepository productRepository) {
        ShoppingCart shoppingCart = getShoppingCart(shoppingCartItemDto, shoppingCartRepository);
        ProductCatalog productCatalog = getProductCatalog(shoppingCartItemDto, productRepository);

        entity.setShoppingCart(shoppingCart);
        entity.setProductCatalog(productCatalog);
    }

    private static ProductCatalog getProductCatalog(ShoppingCartItemCreateEditDto shoppingCartItemDto, ProductRepository productRepository) {
        return productRepository.findById(shoppingCartItemDto.getProductCatalogId()).orElseThrow();
    }

    private static ShoppingCart getShoppingCart(ShoppingCartItemCreateEditDto shoppingCartItemDto, ShoppingCartRepository shoppingCartRepository) {
        return shoppingCartRepository.findById(shoppingCartItemDto.getShoppingCartId()).orElseThrow();
    }
}
