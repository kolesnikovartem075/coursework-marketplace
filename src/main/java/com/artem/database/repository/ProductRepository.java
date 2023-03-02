package com.artem.database.repository;

import com.artem.database.entity.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductCatalog, Long> {
}
