package com.example.springbootapp.data.specification;

import com.example.springbootapp.data.entities.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> usernameContains(String username) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }
}
