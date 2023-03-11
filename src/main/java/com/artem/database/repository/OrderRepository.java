package com.artem.database.repository;

import com.artem.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o FROM Order o
            WHERE o.customer.id = (SELECT c.id FROM Customer c WHERE c.email = :email)
                        """)
    List<Order> findByCustomerEmail(String email);
}
