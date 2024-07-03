package com.example.springbootapp.data.entities;

import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "wishlists")
@Data
@NoArgsConstructor
public class Wishlist {
    @Id
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    private WishlistVisibility visibility;

    @OneToOne(mappedBy = "wishlist", fetch = FetchType.LAZY)
    private Customer owner;

    @ToString.Exclude
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistItem> items;

    @ToString.Exclude
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistAccess> accesses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(getId(), wishlist.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
