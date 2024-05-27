package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(order, orderItem.order) &&
                Objects.equals(product, orderItem.product) &&
                Objects.equals(personalization, orderItem.personalization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product, personalization);
    }
}
