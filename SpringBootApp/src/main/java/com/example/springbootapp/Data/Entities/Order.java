package com.example.springbootapp.Data.Entities;

import com.example.springbootapp.Data.Entities.Embdeddables.AddressEmbeddable;
import com.example.springbootapp.Data.Entities.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

//FIXME: Aggiungere la parte delle transazioni con id ecc
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "total_price", nullable = false, scale = 2)
    private Double totalPrice;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name = "zip_code")),
        @AttributeOverride(name = "civicNumber", column = @Column(name = "civic_number"))
})
    private AddressEmbeddable address;     //Uso un indirizzo embeddable perchè se cancello l'indirizzo, voglio che comunque l'ordine abbia le informazioni sull'indirizzo

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;     //RICORDARE CHE SE IL CLIENTE CANCELLA L'ACCOUNT, LA REFERENZA A CUSTOMER RIMANE MA DOVRò FARE IL CONTROLLO SU NULL SU order.getCustomer()

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items;
}
