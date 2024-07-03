package com.example.springbootapp.reactiveProgramming;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface OrderDaoReactive extends ReactiveMongoRepository<OrderReactive, String>, CustomReactiveOrderDao{

    Mono<Long> countByDateBetween(LocalDateTime start, LocalDateTime end);
    //devo sommare tutti i totalPrice di tutti gli ordini
}
