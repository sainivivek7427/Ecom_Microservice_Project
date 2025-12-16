package com.micro.cart_service.serviceimpl;

import com.micro.cart_service.Entity.Cart;
import com.micro.cart_service.Entity.CartItem;
import com.micro.cart_service.model.CartItemRequest;
import com.micro.cart_service.repository.CartItemRepository;
import com.micro.cart_service.repository.CartRepository;
import com.micro.cart_service.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    public CartItem addToCart(String guestUserCartId, CartItemRequest cartItemRequest){
        Cart cart=cartRepository.findByGuestUserId(guestUserCartId).orElseGet(()->null);
        if(cart==null){
            Cart c = new Cart(); c.setId(UUID.randomUUID().toString()); c.setGuestUserId(guestUserCartId); c.setCreatedDate(System.currentTimeMillis());
            Cart cartSave= cartRepository.save(c);
            CartItem i = new CartItem();
            i.setId(UUID.randomUUID().toString());
            i.setCartId(cartSave.getId());
            i.setProductId(cartItemRequest.getProductId());
            i.setQuantity(cartItemRequest.getQuantity());
            i.setCreatedDate(System.currentTimeMillis());
            i.setGuestUserId(guestUserCartId);
            //call price by using productid
            i.setPrice(cartItemRequest.getQuantity()*90.0);
            return cartItemRepository.save(i);
        }


        CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(), cartItemRequest.getProductId()).orElseGet(() ->null);

//        assert cartItem != null;
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity() + cartItem.getQuantity());
            //call api of price by productid
            cartItem.setPrice((cartItem.getQuantity() + cartItem.getQuantity())*cartItem.getPrice());
            return cartItemRepository.save(cartItem);
        }

        CartItem i = new CartItem();
        i.setId(UUID.randomUUID().toString());
        i.setCartId(cart.getId());
        i.setProductId(cartItemRequest.getProductId());
        i.setQuantity(cartItemRequest.getQuantity());
        i.setCreatedDate(System.currentTimeMillis());
        //call price by using productid
        i.setPrice(cartItemRequest.getQuantity()*90.0);
        i.setGuestUserId(guestUserCartId);
        return cartItemRepository.save(i);

    }

    @Override
    public List<CartItem> getItemsByGuestCartId(String guestCartId){
        return cartItemRepository.findByGuestUserId(guestCartId);
    }

    @Transactional
    public void mergeGuestToUserCart(String guestUserId, String userId) {
        Cart guestCart = cartRepository.findByGuestUserId(guestUserId).orElse(null);
        Cart userCart  = cartRepository.findByGuestUserId(userId).orElse(null);

        if (guestCart == null) return;
        if (userCart == null) {
            guestCart.setGuestUserId(userId);
            cartRepository.save(guestCart);
            return;
        }

        List<CartItem> guestItems = cartItemRepository.findByCartId(guestCart.getId());

        for (CartItem guestItem : guestItems) {

            CartItem userItem =
                    cartItemRepository.findByCartIdAndProductId(
                            userCart.getId(),
                            guestItem.getProductId()
                    ).orElse(null);

            if (userItem != null) {
                // SAME PRODUCT → MERGE QTY
                userItem.setQuantity(
                        userItem.getQuantity() + guestItem.getQuantity()
                );
                //call price by using productid
                userItem.setPrice((userItem.getQuantity() + guestItem.getQuantity())*90.0);
                cartItemRepository.save(userItem);

                cartItemRepository.deleteById(guestItem.getId());

            } else {
                // PRODUCT ONLY IN GUEST CART → MOVE
                CartItem newItem = new CartItem();
                newItem.setCartId(userCart.getId());
                newItem.setProductId(guestItem.getProductId());
                newItem.setQuantity(guestItem.getQuantity());
                newItem.setPrice(guestItem.getQuantity()*90.0);
                cartItemRepository.save(newItem);
                cartItemRepository.deleteById(guestItem.getId());
            }
        }

        cartRepository.deleteById(guestCart.getId());
    }
}
