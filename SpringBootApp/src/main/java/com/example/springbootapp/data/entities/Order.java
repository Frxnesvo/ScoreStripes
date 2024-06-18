package com.example.springbootapp.data.entities;


import com.example.springbootapp.data.entities.Embdeddables.OrderInformations;
import com.example.springbootapp.data.entities.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//FIXME: Aggiungere la parte delle transazioni con id ecc
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "total_price", nullable = false, scale = 2)
    private Double totalPrice;

    @Embedded
    private OrderInformations resilientInfos;     //uso queste informazioni per evitare di perdere dati in caso di cancellazione di un account

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;     //RICORDARE CHE SE IL CLIENTE CANCELLA L'ACCOUNT, LA REFERENZA A CUSTOMER RIMANE MA DOVRò FARE IL CONTROLLO SU NULL SU order.getCustomer()

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        if(items == null || items.isEmpty()){
            this.totalPrice = 0.0;
            return;
        }
        this.totalPrice = items.stream().mapToDouble(OrderItem::getPrice).sum();
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
