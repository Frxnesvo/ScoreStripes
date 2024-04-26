package com.example.springbootapp.Data.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "clubs")
@Data
@NoArgsConstructor
public class Club {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pic_url", nullable = false)
    private String picUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;
}
