package com.example.springbootapp.Data.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("admin")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{

}
