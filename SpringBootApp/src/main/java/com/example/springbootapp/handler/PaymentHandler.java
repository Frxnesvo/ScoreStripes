package com.example.springbootapp.handler;

import com.example.springbootapp.data.entities.Order;
import com.example.springbootapp.data.entities.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private final ModelMapper modelMapper;

    @Value("${stripe.apikey}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    public String startCheckoutProcess(Order order) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            lineItems.add(createLineItem(item));
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PAYPAL)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://192.168.1.55:8080/stripe_success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://192.168.1.55:8080/stripe_cancel?session_id={CHECKOUT_SESSION_ID}")
                .putMetadata("orderId", order.getId())
                .build();
        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean checkTransactionStatus(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        return session.getPaymentStatus().equals("paid");
    }

    public String getOrderIdFromSession(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        return session.getMetadata().get("orderId");
    }


    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()     // Crea il LineItem con nome e descrizione senza immagini
                .setQuantity(item.getQuantity().longValue())
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(Math.round(item.getPrice() * 100)/ item.getQuantity().longValue())
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(item.getProduct().getProduct().getName())
                                .setDescription(item.getProduct().getProduct().getDescription())
                                .build())
                        .build())
                .build();
    }




//    private SessionCreateParams.LineItem createLineItem(OrderItem item) { // IMPLEMENTAZIONE PER CARICARE I PRODOTTI ANCHE SU STRIPE. AL MOMENTO NON LO IMPLEMENTO (PRENDE TROPPO TEMPO)
//        return SessionCreateParams.LineItem.builder()
//                .setQuantity(item.getQuantity().longValue())
//                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                        .setCurrency("eur")
//                        .setUnitAmount(Math.round(item.getPrice() * 100))
//                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                .setName(item.getProduct().getProduct().getName())
//                                .setDescription(item.getProduct().getProduct().getDescription())
//                                .addAllImage(item.getProduct().getProduct().getPics().stream()
//                                        .map(pic -> modelMapper.map(pic, String.class))
//                                        .collect(Collectors.toList()))
//                                .build())
//                        .build())
//                .build();
//    }

//    public String createProductInStripe(ProductDto product){  // IMPLEMENTAZIONE PER CARICARE I PRODOTTI ANCHE SU STRIPE. AL MOMENTO NON LO IMPLEMENTO (PRENDE TROPPO TEMPO)
//        ProductCreateParams params = ProductCreateParams.builder()
//                .setName(product.getName())
//                .setDescription(product.getDescription())
//                .addAllImage(product.getPics().stream()
//                        .map(pic -> modelMapper.map(pic, String.class))
//                        .collect(Collectors.toList()))
//                .build();
//        try {
//            Product stripeProduct = Product.create(params);
//            return stripeProduct.getId();
//        } catch (StripeException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
