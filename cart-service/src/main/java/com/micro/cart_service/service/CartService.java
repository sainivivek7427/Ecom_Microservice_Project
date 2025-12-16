package com.micro.cart_service.service;

import com.micro.cart_service.Entity.CartItem;
import com.micro.cart_service.model.CartItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    public CartItem addToCart(String guestUserCartId, CartItemRequest cartItemRequest);

    public List<CartItem> getItemsByGuestCartId(String guestCartId);
}
