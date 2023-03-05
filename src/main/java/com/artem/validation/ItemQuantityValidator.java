package com.artem.validation;

import com.artem.dto.ProductReadDto;
import com.artem.dto.ShoppingCartItemCreateEditDto;
import com.artem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ItemQuantityValidator implements ConstraintValidator<ItemQuantity, ShoppingCartItemCreateEditDto> {

    private final ProductService productService;

    @Override
    public boolean isValid(ShoppingCartItemCreateEditDto itemDto, ConstraintValidatorContext context) {
        ProductReadDto productDto = getProduct(itemDto);

        return productDto.getQuantity() > itemDto.getQuantity();
    }

    private ProductReadDto getProduct(ShoppingCartItemCreateEditDto itemDto) {
        return productService.findById(itemDto.getProductCatalogId())
                .orElseThrow();
    }
}
