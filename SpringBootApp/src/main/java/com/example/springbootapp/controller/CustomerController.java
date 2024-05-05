package com.example.springbootapp.controller;

import com.example.springbootapp.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins ="*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


}
