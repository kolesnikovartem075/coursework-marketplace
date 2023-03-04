package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.dto.ProductCreateEditDto;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper
public interface ProductCreateEditMapper {

    default ProductCatalog map(ProductCreateEditDto productCreateEditDto) {
        ProductCatalog productCatalog = new ProductCatalog();
        copy(productCreateEditDto, productCatalog);

        return productCatalog;
    }

    default ProductCatalog map(ProductCreateEditDto fromObject, ProductCatalog toObject) {
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