package com.example.springbootapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StripeRedirectController {   //Potrebbe essere anche tolta al momento poichè il frontend gestisce la redirezione di Stripe senza bisogno di questo. Lo lascio per implementazioni future

    @GetMapping("/stripe_success")
    public String stripeSuccess(Model model, HttpServletRequest request) {
        return "stripe_success";
    }

    @GetMapping("/stripe_cancel")
    public String stripeCancel(Model model, HttpServletRequest request) {
        return "stripe_cancel";
    }
}
