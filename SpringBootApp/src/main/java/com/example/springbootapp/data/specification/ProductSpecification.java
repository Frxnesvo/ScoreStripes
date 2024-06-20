package com.example.springbootapp.data.specification;

import com.example.springbootapp.data.entities.Enums.OrderStatus;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.data.entities.Order;
import com.example.springbootapp.data.entities.OrderItem;
import com.example.springbootapp.data.entities.Product;
import com.example.springbootapp.data.entities.ProductWithVariant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> topSellingProducts(ProductCategory category) {
        return (root, query, builder) -> {
            Join<Product, ProductWithVariant> productWithVariants = root.join("variants");
            Join<ProductWithVariant, OrderItem> orderItems = productWithVariants.join("orderItems");
            Join<OrderItem, Order> orders = orderItems.join("order");

            Predicate productTypePredicate = builder.equal(root.get("category"), category.name());
            Predicate orderStatusPredicate = builder.equal(orders.get("status"), OrderStatus.COMPLETED.name());

            query.groupBy(root.get("id"));
            query.orderBy(builder.desc(builder.count(orders.get("id"))));

            return builder.and(productTypePredicate, orderStatusPredicate);
        };
    }
}
