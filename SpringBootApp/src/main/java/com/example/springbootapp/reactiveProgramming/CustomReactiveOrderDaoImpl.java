package com.example.springbootapp.reactiveProgramming;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
@RequiredArgsConstructor
public class CustomReactiveOrderDaoImpl implements CustomReactiveOrderDao{

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Double> getTotalEarnings() {
        Aggregation aggregation = newAggregation(
                group().sum("totalPrice").as("totalEarnings")
        );

        return reactiveMongoTemplate.aggregate(aggregation, "orders", TotalEarnings.class)
                .next()
                .map(TotalEarnings::getTotalEarnings);
    }

    public static class TotalEarnings {
        private double totalEarnings;

        public double getTotalEarnings() {
            return totalEarnings;
        }

        public void setTotalEarnings(double totalEarnings) {
            this.totalEarnings = totalEarnings;
        }
    }
}
