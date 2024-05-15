package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Enums.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "products_with_variant", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "size"})
})
@Data
@NoArgsConstructor
public class ProductWithVariant {

    public ProductWithVariant(Size size, Integer availability, Product product) {
        this.size = size;
        this.availability = availability;
        this.product = product;
    }

    @Id
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column(name = "availability", nullable = false)
    private Integer availability;

    @ManyToOne(fetch = FetchType.LAZY)  //devo caricare lazy perchè altrimenti dato che dal prodotto carico eager le varianti mi ritrovo in un loop
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
