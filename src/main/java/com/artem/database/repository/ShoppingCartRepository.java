package com.artem.database.repository;

import com.artem.database.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomerId(Long customerId);

    @Query("""
            SELECT sc FROM ShoppingCart sc
            WHERE sc.customer.id = (SELECT c.id FROM Customer c WHERE c.email = :email)
                        """)
    Optional<ShoppingCart> findByCustomerEmail(String email);
}
