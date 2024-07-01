package com.example.springbootapp.data.specification;

import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.data.entities.Enums.OrderStatus;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> topSellingProducts(ProductCategory category) {
        return (root, query, criteriaBuilder) -> {
            // Subquery per contare gli ordini completati per ogni prodotto
            Subquery<Long> orderCountSubquery = query.subquery(Long.class);
            Root<OrderItem> orderItemRoot = orderCountSubquery.from(OrderItem.class);
            Join<OrderItem, ProductWithVariant> variantJoin = orderItemRoot.join("product");
            Join<ProductWithVariant, Product> productJoin = variantJoin.join("product");
            Join<OrderItem, Order> orderJoin = orderItemRoot.join("order");
            orderCountSubquery.select(criteriaBuilder.count(orderItemRoot))
                    .where(
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(productJoin.get("id"), root.get("id")),
                                    criteriaBuilder.equal(orderJoin.get("status"), OrderStatus.COMPLETED)
                            )
                    );
            // Predicato per filtrare per categoria
            Predicate categoryPredicate = criteriaBuilder.equal(root.get("category"), category);

            // Ordinamento per numero di ordini decrescente
            query.orderBy(criteriaBuilder.desc(orderCountSubquery));

            // Selezione solo dei prodotti con almeno un ordine completato
            Predicate hasOrdersPredicate = criteriaBuilder.greaterThan(orderCountSubquery, 0L);

            return criteriaBuilder.and(categoryPredicate, hasOrdersPredicate);
        };
    }

    public static Specification<Product> withFilters(String name, String leagueName, String category, String clubName) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (leagueName != null && !leagueName.isEmpty()) {
                Join<Product, Club> clubJoin = root.join("club");
                Join<Club, League> leagueJoin = clubJoin.join("league");
                predicates.add(builder.equal(leagueJoin.get("name"), leagueName));
            }
            if (category != null && !category.isEmpty()) {
                predicates.add(builder.equal(root.get("category"), category));
            }
            if (clubName != null && !clubName.isEmpty()) {
                predicates.add(builder.equal(root.get("club").get("name"), clubName));
            }
            return builder.and(predicates.toArray(new Predicate[0]));   // Converte i filtri in una singola clausola con AND
        };
    }
}
