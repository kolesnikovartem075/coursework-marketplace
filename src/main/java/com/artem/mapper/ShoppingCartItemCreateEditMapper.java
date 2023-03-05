package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.database.entity.ShoppingCart;
import com.artem.database.entity.ShoppingCartItem;
import com.artem.database.repository.ProductRepository;
import com.artem.database.repository.ShoppingCartRepository;
import com.artem.dto.ShoppingCartItemCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShoppingCartItemCreateEditMapper implements Mapper<ShoppingCartItemCreateEditDto, ShoppingCartItem> {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartItem map(ShoppingCartItemCreateEditDto object) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        ShoppingCart shoppingCart = getShoppingCart(object);
        ProductCatalog productCatalog = getProductCatalog(object);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setProductCatalog(productCatalog);
        copy(object, shoppingCartItem);

        return shoppingCartItem;
    }

    @Override
    public ShoppingCartItem map(ShoppingCartItemCreateEditDto fromObject, ShoppingCartItem toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ShoppingCartItemCreateEditDto object, ShoppingCartItem shoppingCartItem) {
        shoppingCartItem.setQuantity(object.getQuantity());
    }


    private ProductCatalog getProductCatalog(ShoppingCartItemCreateEditDto shoppingCartItemDto) {
        return productRepository.findById(shoppingCartItemDto.getProductCatalogId())
                .orElseThrow();
    }

    private ShoppingCart getShoppingCart(ShoppingCartItemCreateEditDto shoppingCartItemDto) {
        return shoppingCartRepository.findById(shoppingCartItemDto.getShoppingCartId())
                .orElseThrow();
    }
}
