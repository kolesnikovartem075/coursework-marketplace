package com.artem.mapper;

import com.artem.database.entity.ProductCatalog;
import com.artem.dto.CustomerReadDto;
import com.artem.dto.ProductReadDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductReadMapper {
    ProductReadDto map(ProductCatalog product);
}
