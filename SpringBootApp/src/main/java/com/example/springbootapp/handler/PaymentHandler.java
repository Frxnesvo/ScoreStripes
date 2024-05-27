package com.example.springbootapp.handler;

import com.example.springbootapp.data.entities.Order;
import com.example.springbootapp.data.entities.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private final ModelMapper modelMapper;

    @Value("${stripe.apikey}")
    private String stripeApiKey;


    public String startCheckoutProcess(Order order) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            lineItems.add(createLineItem(item));
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PAYPAL)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("DEEP_LINK_SUCCESS")  //TODO
                .setCancelUrl("DEEP_LINK_CANCEL")    //TODO
                .putMetadata("orderId", order.getId())
                .build();
        Session session = Session.create(params);
        return session.getUrl();
    }

    public Boolean checkPaymentStatus(String sessionId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        Session session = Session.retrieve(sessionId);
        return session.getPaymentStatus().equals("paid");
    }


    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(item.getQuantity().longValue())
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(Math.round(item.getPrice() * 100))
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(item.getProduct().getProduct().getName())
                                .setDescription(item.getProduct().getProduct().getDescription())
                                .addAllImage(item.getProduct().getProduct().getPics().stream()
                                        .map(pic -> modelMapper.map(pic, String.class))
                                        .collect(Collectors.toList()))
                                .build())
                        .build())
                .build();
    }


}
