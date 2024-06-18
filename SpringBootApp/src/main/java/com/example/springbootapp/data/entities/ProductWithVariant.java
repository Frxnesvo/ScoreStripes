package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Enums.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

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
    @ToString.Exclude  //TODO: STO TESTANDO
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithVariant that = (ProductWithVariant) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
