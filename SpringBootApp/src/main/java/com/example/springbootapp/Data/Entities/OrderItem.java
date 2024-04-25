package com.example.springbootapp.Data.Entities;

import com.example.springbootapp.Data.Entities.Embdeddables.Personalization;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "PlayerName", column = @Column(name = "player_name")),
        @AttributeOverride(name = "PlayerNumber", column = @Column(name = "player_number"))
    })
    private Personalization personalization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
