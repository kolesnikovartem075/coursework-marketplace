package com.artem.database.entity;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = "order")
@EqualsAndHashCode(exclude = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_order_line")
public class OrderLine implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_order_id")
    private Order order;

    @ManyToOne
    private ProductCatalog productCatalog;

    private Integer quantity;
    private Integer price;
}
