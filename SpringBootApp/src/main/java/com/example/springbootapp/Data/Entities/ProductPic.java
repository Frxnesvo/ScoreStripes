package com.example.springbootapp.Data.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "product_pics")
@Data
@NoArgsConstructor
public class ProductPic {
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
