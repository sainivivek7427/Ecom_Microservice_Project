package com.micro.cart_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @GetMapping("/msg")
    public ResponseEntity<?> getMsg(){
        return ResponseEntity.ok("Cart service working");
    }
}
