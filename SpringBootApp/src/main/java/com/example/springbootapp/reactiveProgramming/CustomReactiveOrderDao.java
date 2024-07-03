package com.example.springbootapp.reactiveProgramming;

import reactor.core.publisher.Mono;

public interface CustomReactiveOrderDao {
    Mono<Double> getTotalEarnings();
}
