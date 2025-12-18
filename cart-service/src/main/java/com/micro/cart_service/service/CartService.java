package com.micro.cart_service.service;

import com.micro.cart_service.Entity.Cart;
import com.micro.cart_service.Entity.CartItem;
import com.micro.cart_service.model.CartItemRequest;
import com.micro.cart_service.model.CartItemResponse;
import com.micro.cart_service.model.CartItemUpdateDeleteRequest;
import com.micro.cart_service.model.MergeGuestToUserCartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    public CartItem addToCart(String guestUserCartId, CartItemRequest cartItemRequest);

    public List<CartItem> getItemsByGuestCartId(String guestCartId);

    public Cart initCart(String guestUserId);

    public CartItemResponse getCartPageAllCartItems(String cartId, String guestUserId);

    public CartItem updateQuantity(String cartItemId, CartItemUpdateDeleteRequest cartItemUpdateDeleteRequest) ;
    public void removeItem(String cartId, String cartItemId, String guestUserId);

    public MergeGuestToUserCartResponse mergeGuestCartToUserCart(String guestUserId, String userId);
}
