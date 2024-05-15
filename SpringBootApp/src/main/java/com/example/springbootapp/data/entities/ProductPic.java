package com.example.springbootapp.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "product_pics")
@Data
@NoArgsConstructor
public class ProductPic {

    public ProductPic(String picUrl, Boolean principal, Product product) {
        this.picUrl = picUrl;
        this.principal = principal;
        this.product = product;
    }

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "pic_url", nullable = false, unique = true)
    private String picUrl;

    @Column(name = "principal", nullable = false)
    private Boolean principal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
