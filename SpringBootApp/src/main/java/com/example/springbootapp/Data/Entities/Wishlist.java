package com.example.springbootapp.Data.Entities;

import com.example.springbootapp.Data.Entities.Enums.WishlistVisibility;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "wishlists")
@Data
@NoArgsConstructor
public class Wishlist {
    @Id
    @UuidGenerator
    private String id;

    @Enumerated
    private WishlistVisibility visibility;

    @OneToOne(mappedBy = "wishlist", fetch = FetchType.LAZY)   // Mantengo la bidirezionalità al momento perchè potrebbe servire
    private Customer customer;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistItem> items;
}
