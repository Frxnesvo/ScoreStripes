package com.example.springbootapp.reactiveProgramming;

import com.example.springbootapp.data.entities.Enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderServiceReactive {

    private final OrderDaoReactive orderDaoReactive;
    private final Sinks.Many<OrderControllerReactive.SummaryData> sink;
    private final Sinks.Many<OrderReactive> orderSink;

    @Autowired
    public OrderServiceReactive(OrderDaoReactive orderDaoReactive) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.orderDaoReactive = orderDaoReactive;
        this.orderSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Mono<Long> countOrdersToday() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return orderDaoReactive.countByDateBetween(start, end);
    }

    public Mono<Long> countOrders(){
        return orderDaoReactive.count();}

    public Mono<Double> totalEarnings(){
        return orderDaoReactive.getTotalEarnings();
    }


    public void addProduct() {
        System.out.println("Adding product");
        OrderReactive orderReactive = new OrderReactive();
        orderReactive.setTotalPrice(150.0);
        orderReactive.setDate(LocalDateTime.now().plusHours(12).toString());
        orderReactive.setStatus(OrderStatus.PENDING.toString());
        orderDaoReactive.save(orderReactive)
        .then(Mono.zip(countOrders(), totalEarnings()))
                .map(tuple -> new OrderControllerReactive.SummaryData(tuple.getT1(), tuple.getT2()))
                .doOnNext(summary ->{
                    System.out.println("Attempting to emit summary: " + summary);
                    Sinks.EmitResult result = sink.tryEmitNext(summary);
                    if(result.isFailure()){
                        System.out.println("Emission failed: " + result);
                    }
                    else {
                        System.out.println("Emission succeeded: " + result);
                    }
                })
                .subscribe();
        Sinks.EmitResult result = orderSink.tryEmitNext(orderReactive);
        if(result.isFailure()){
            System.out.println("new order failed: " + result);
        }
        else {
            System.out.println("new oerder succeeded: " + result);
        }

    }

    public Flux<OrderControllerReactive.SummaryData> getSummaryDataUpdates() {
        return sink.asFlux();  //dovrebbe far si che il sink emetta i dati ovvero il summary
    }

    public Flux<OrderReactive> getNewOrdersStream() {
        System.out.println("STO ENTRANDO NELLO STREAM DEI NUOVI ORDINI");
        return orderSink.asFlux();
    }
}
