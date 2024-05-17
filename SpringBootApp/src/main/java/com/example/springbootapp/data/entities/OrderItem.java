package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "PlayerName", column = @Column(name = "player_name")),
        @AttributeOverride(name = "PlayerNumber", column = @Column(name = "player_number"))
    })
    private Personalization personalization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductWithVariant product;

    private void calculatePrice() {
        this.price = (product.getProduct().getPrice() + personalization.getPrice()) * quantity;
    }

    public double getPrice() {
        if (this.price == 0.0) {
            calculatePrice();
        }
        return this.price;
    }
}