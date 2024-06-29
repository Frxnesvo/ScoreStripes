package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.WishlistAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistAccessDao extends JpaRepository<WishlistAccess, String> {
    @Modifying
    @Query("DELETE FROM WishlistAccess wa WHERE wa.wishlist.id = :wishlistId")
    void deleteByWishlistId(String wishlistId);

    List<WishlistAccess> findAllByGuestId(String id);
    List<WishlistAccess> findAllByWishlistId(String id);
    Optional<WishlistAccess> findByWishlistIdAndGuestId(String id, String guestId);
    Boolean existsByWishlistIdAndGuestId(String id, String guestId);
}

