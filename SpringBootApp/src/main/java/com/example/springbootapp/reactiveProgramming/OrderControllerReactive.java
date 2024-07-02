package com.example.springbootapp.reactiveProgramming;

import com.example.springbootapp.data.entities.Enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/orders/reactive")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderControllerReactive {
    private final OrderServiceReactive orderServiceReactive;
    private final OrderDaoReactive orderDaoReactive;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> countOrdersToday() {
        OrderReactive orderReactive = new OrderReactive();
        orderReactive.setStatus(OrderStatus.PENDING.toString());
        orderReactive.setTotalPrice(100.0);
        orderDaoReactive.save(orderReactive).subscribe();
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> orderServiceReactive.countOrders())
                .doOnNext(count -> System.out.println("Sending count: " + count))
                .doOnError(error -> System.err.println("Error in stream: " + error))
                .doOnCancel(() -> System.out.println("Stream canceled (countOrdersToday)"));
    }

    @GetMapping("/count")
    public Mono<Long> countOrders() {
        return orderServiceReactive.countOrders();
    }

    @GetMapping("/initial")
    public Mono<OrderControllerReactive.SummaryData> getInitialSummaryData() {
        System.out.println("E STATA CHIAMATA INITIAL");
        Long orderCount = orderServiceReactive.countOrders().block();
        Double totalEarnings = orderServiceReactive.totalEarnings().block();
        System.out.println("INIZIALIIII orderCount: " + orderCount + " totalEarnings: " + totalEarnings);
        return Mono.just(new SummaryData(orderCount, totalEarnings));
    }

    @GetMapping(value="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SummaryData> getSummaryData() {
        System.out.println("E STATA CHIAMATA STREAM");
        return orderServiceReactive.getSummaryDataUpdates()
                .doOnNext(summary -> System.out.println("Sending summary: " + summary.getTotalEarnings().toString() + " " + summary.getOrderCount().toString()))
                .doOnError(error -> System.err.println("Error in stream: " + error))
                .doOnCancel(() -> System.out.println("Stream canceled"));
    }

    @GetMapping("/add")
    public void addProduct() {
        orderServiceReactive.addProduct();
    }

    @GetMapping(value="/newOrdersStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderReactive> getNewOrdersStream() {
        System.out.println("E STATA CHIAMATA NEW ORDERS STREAM");
        return orderServiceReactive.getNewOrdersStream()
                .doOnNext(order -> System.out.println("Sending order: " + order.getId()))
                .doOnError(error -> System.err.println("Error in stream: " + error))
                .doOnCancel(() -> System.out.println("Stream canceled"));
    }



    static class SummaryData {
        private final Long orderCount;
        private final Double totalEarnings;

        public SummaryData(Long orderCount, Double totalEarnings) {
            this.orderCount = orderCount;
            this.totalEarnings = totalEarnings;
        }

        public Long getOrderCount() {
            return orderCount;
        }

        public Double getTotalEarnings() {
            return totalEarnings;
        }
    }
}

