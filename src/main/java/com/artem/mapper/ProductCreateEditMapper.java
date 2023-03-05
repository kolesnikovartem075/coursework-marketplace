package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.dto.ProductCreateEditDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, ProductCatalog> {

    @Override
    public ProductCatalog map(ProductCreateEditDto productCreateEditDto) {
        ProductCatalog productCatalog = new ProductCatalog();
        copy(productCreateEditDto, productCatalog);

        return productCatalog;
    }

    @Override
    public ProductCatalog map(ProductCreateEditDto fromObject, ProductCatalog toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private static void copy(ProductCreateEditDto fromObject, ProductCatalog toObject) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setDescription(fromObject.getDescription());
        toObject.setQuantity(fromObject.getQuantity());
        toObject.setPrice(fromObject.getPrice());

        Optional.ofNullable(fromObject.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> toObject.setImage(image.getOriginalFilename()));
    }
}