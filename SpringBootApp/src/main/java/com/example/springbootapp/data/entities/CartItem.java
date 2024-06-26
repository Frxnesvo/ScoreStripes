package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Embdeddables.Personalization;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CartItem {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "PlayerName", column = @Column(name = "player_name")),
            @AttributeOverride(name = "PlayerNumber", column = @Column(name = "player_number"))
    })
    private Personalization personalization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductWithVariant product;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    public Double getPrice(){
        if(personalization == null)
            return product.getProduct().getPrice() * quantity;
        return (product.getProduct().getPrice() + personalization.getPrice()) * quantity;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cart, cartItem.cart) &&
                Objects.equals(product, cartItem.product) &&
                Objects.equals(personalization, cartItem.personalization);
    }

    @Override
    public int hashCode(){
        return Objects.hash(cart, product, personalization);
    }
}
