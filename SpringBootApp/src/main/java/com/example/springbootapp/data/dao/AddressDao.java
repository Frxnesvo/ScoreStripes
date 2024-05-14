package com.example.springbootapp.data.dao;

import com.example.springbootapp.data.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<Address, String> {
    List<Address> findByCustomerId(String customerId);
}
