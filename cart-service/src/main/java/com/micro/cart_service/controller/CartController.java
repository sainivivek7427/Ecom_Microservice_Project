package com.micro.cart_service.controller;

import com.micro.cart_service.Entity.Cart;
import com.micro.cart_service.model.CartItemRequest;
import com.micro.cart_service.model.CartItemUpdateDeleteRequest;
import com.micro.cart_service.model.MergeGuestToUserCartResponse;
import com.micro.cart_service.repository.CartRepository;
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

    @PostMapping("/init")
    public ResponseEntity<?> cartInitialize(@RequestHeader("X-GUEST-ID") String guestUserId) {
        return ResponseEntity.ok(cartService.initCart(guestUserId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCartProduct(@RequestBody CartItemRequest cartItemRequest,@RequestParam("cartId") String cartId ){
        System.out.println("cart id "+cartId);
        System.out.println("cartitem "+cartItemRequest.getQuantity());
        return ResponseEntity.ok(cartService.addToCart(cartId,cartItemRequest));
//        return null;
    }


    @GetMapping("/cart-products-items")
    public ResponseEntity<?> getCartProductResponse(@RequestHeader("X-GUEST-ID") String guestUserId,@RequestParam String cartId){
        return ResponseEntity.ok(cartService.getCartPageAllCartItems(cartId,guestUserId));
    }

    @PutMapping("/cart-item")
    public ResponseEntity<?> updateCartItem( @RequestParam String cartItemId, @RequestBody CartItemUpdateDeleteRequest cartItemUpdateDeleteRequest){
        return ResponseEntity.ok(cartService.updateQuantity(cartItemId,cartItemUpdateDeleteRequest));
    }

    @DeleteMapping("/cart-item/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@RequestHeader("X-GUEST-ID") String guestUserId,@PathVariable("cartItemId") String cartItemId,@RequestParam("cartId") String cartId ){
        cartService.removeItem(cartId,cartItemId,guestUserId);
        return ResponseEntity.ok("Succcessgully deleted");
    }

    @GetMapping("/items")
    public ResponseEntity<?> items(@RequestParam("guestCartId") String guestCartId) {
        return ResponseEntity.ok(cartService.getItemsByGuestCartId(guestCartId));
    }

    @GetMapping("/merge/guest-user/{userId}")
    public ResponseEntity<MergeGuestToUserCartResponse> mergeGuestToUserCart(@PathVariable("userId") String userId,@RequestParam("guestUserId") String guestUserId){
        return ResponseEntity.ok(cartService.mergeGuestCartToUserCart(guestUserId,userId));
    }

    @Autowired
    CartRepository cartRepository;
    @GetMapping("/getall")
    public ResponseEntity<?> getAllCarts(){
        return ResponseEntity.ok(cartRepository.findAll());
    }


}
