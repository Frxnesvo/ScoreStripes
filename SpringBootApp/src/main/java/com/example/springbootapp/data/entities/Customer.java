package com.example.springbootapp.data.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@DiscriminatorValue("customer")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    @Column(name = "favourite_team")
    private String favouriteTeam;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToOne(cascade =  CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")  //vorrei mettere nullable = false ma da problemi con la singletable e admin
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wishlist_id")  //vorrei mettere nullable = false ma da problemi con la singletable e admin
    private Wishlist wishlist;

    @OneToMany(mappedBy = "customer", cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Order> orders;

}
