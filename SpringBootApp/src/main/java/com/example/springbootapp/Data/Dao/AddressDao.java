package com.example.springbootapp.Data.Dao;

import com.example.springbootapp.Data.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, String> {
}
