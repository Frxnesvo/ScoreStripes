package com.example.springbootapp.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "access_token_blacklist")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AccessTokenBlacklist {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;  //uso Date perchè la specifica JWT richiede che le date siano in formato Unix Timestamp

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
}
