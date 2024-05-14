package com.example.springbootapp.data.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @UuidGenerator
    private String id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    Integer size() {
        return cartItems.size();
    }

    Double totalPrice() {
        return cartItems.stream().mapToDouble(CartItem::getPrice).sum();
    }

}
