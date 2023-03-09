package com.artem.database.repository;

import com.artem.database.entity.PaymentMethod;
import com.artem.mapper.PaymentMethodReadMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("""
            SELECT pm FROM PaymentMethod pm
            WHERE pm.customer.id = (SELECT c.id FROM Customer c WHERE c.email = :email)
                        """)
    Optional<PaymentMethod> findByCustomerEmail(String email);

    Optional<PaymentMethod> findByCustomerId(Long costumerId);
}
