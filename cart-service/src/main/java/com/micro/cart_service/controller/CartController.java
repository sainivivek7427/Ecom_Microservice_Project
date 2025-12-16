package com.micro.cart_service.controller;

import com.micro.cart_service.model.CartItemRequest;
import com.micro.cart_service.service.CartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/msg")
    public ResponseEntity<?> getMsg(){
        return ResponseEntity.ok("Cart service working");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCartProduct(@RequestBody CartItemRequest cartItemRequest,@RequestParam("guestUserCartId") String guestUserCartId ){
        return ResponseEntity.ok(cartService.addToCart(guestUserCartId,cartItemRequest));
    }

    @GetMapping("/items")
    public ResponseEntity<?> items(@RequestParam String guestCartId) {
        return ResponseEntity.ok(cartService.getItemsByGuestCartId(guestCartId));
    }
}
