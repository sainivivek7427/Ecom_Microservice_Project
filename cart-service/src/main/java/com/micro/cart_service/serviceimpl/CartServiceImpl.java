package com.micro.cart_service.serviceimpl;

import com.micro.cart_service.Entity.Cart;
import com.micro.cart_service.Entity.CartItem;
import com.micro.cart_service.feignClient.ProductClient;
import com.micro.cart_service.model.*;
import com.micro.cart_service.repository.CartItemRepository;
import com.micro.cart_service.repository.CartRepository;
import com.micro.cart_service.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductClient productClient;


    /* ---------- INIT CART ---------- */
    @Override
    public Cart initCart(String guestUserId) {
        return cartRepository.findByGuestUserId(guestUserId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setId(UUID.randomUUID().toString());
                    cart.setGuestUserId(guestUserId);
                    cart.setCreatedDate(System.currentTimeMillis());
                    return cartRepository.save(cart);
                });
    }


    @Override
    public CartItem addToCart(String cartId, CartItemRequest cartItemRequest) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getGuestUserId().equals(cartItemRequest.getGuestUserId())) {
            throw new RuntimeException("Cart ownership mismatch");
        }

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cartId, cartItemRequest.getProductId())
                .orElse(null);
        System.out.println("Cart item "+cartItem);
//        System.out.println(cartItem.getQuantity());

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setId(UUID.randomUUID().toString());
            cartItem.setCartId(cartId);
            cartItem.setProductId(cartItemRequest.getProductId());

            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setGuestUserId(cartItemRequest.getGuestUserId());
            cartItem.setCreatedDate(System.currentTimeMillis());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        }

        return cartItemRepository.save(cartItem);
    }

    public CartItem updateQuantity(String cartItemId, CartItemUpdateDeleteRequest cartItemUpdateDeleteRequest) {
        Cart cart = cartRepository.findById(cartItemUpdateDeleteRequest.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getGuestUserId().equals(cartItemUpdateDeleteRequest.getGuestUserId())) {
            throw new RuntimeException("Unauthorized");
        }

        CartItem item = cartItemRepository
                .findByIdAndCartId(cartItemId, cartItemUpdateDeleteRequest.getCartId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        int newQty = getNewQty(cartItemUpdateDeleteRequest, item);

        //update quantity in product stock and check api
        Boolean checkStockAndUpdate=productClient.checkStock(item.getProductId(),newQty).getBody();
        if(checkStockAndUpdate.equals(Boolean.TRUE)){
            if (newQty <= 0) {
                cartItemRepository.delete(item);
                return item;
            } else {
                item.setQuantity(newQty);
                return cartItemRepository.save(item);
            }
        }

        throw new RuntimeException("Out of stock, cannot update quantity");

    }

    private static int getNewQty(CartItemUpdateDeleteRequest cartItemUpdateDeleteRequest, CartItem item) {
        int newQty =0;
        if(cartItemUpdateDeleteRequest.getAction().equals("ADD")){
            newQty= item.getQuantity() + cartItemUpdateDeleteRequest.getQuantity();
        }
        else if(cartItemUpdateDeleteRequest.getAction().equals("MINUS")){
            if(item.getQuantity()> cartItemUpdateDeleteRequest.getQuantity()){
                newQty= item.getQuantity() - cartItemUpdateDeleteRequest.getQuantity();
            }
            else{
                newQty= cartItemUpdateDeleteRequest.getQuantity()- item.getQuantity() ;
            }

        }
        return newQty;
    }

    @Transactional
    public void removeItem(String cartId, String cartItemId, String guestUserId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getGuestUserId().equals(guestUserId)) {
            throw new RuntimeException("Unauthorized");
        }

        cartItemRepository.deleteByIdAndCartId(cartItemId, cartId);
    }


    @Override
    public List<CartItem> getItemsByGuestCartId(String guestCartId){
        return cartItemRepository.findByGuestUserId(guestCartId);
    }

//    @Transactional
//    public void mergeGuestToUserCart(String guestUserId, String userId) {
//        Cart guestCart = cartRepository.findByGuestUserId(guestUserId).orElse(null);
//        Cart userCart  = cartRepository.findByGuestUserId(userId).orElse(null);
//
//        if (guestCart == null) return;
//        if (userCart == null) {
//            guestCart.setGuestUserId(userId);
//            cartRepository.save(guestCart);
//            return;
//        }
//
//        List<CartItem> guestItems = cartItemRepository.findByCartId(guestCart.getId());
//
//        for (CartItem guestItem : guestItems) {
//
//            CartItem userItem =
//                    cartItemRepository.findByCartIdAndProductId(
//                            userCart.getId(),
//                            guestItem.getProductId()
//                    ).orElse(null);
//
//            if (userItem != null) {
//                // SAME PRODUCT → MERGE QTY
//                userItem.setQuantity(
//                        userItem.getQuantity() + guestItem.getQuantity()
//                );
//                //call price by using productid
//                userItem.setPrice((userItem.getQuantity() + guestItem.getQuantity())*90.0);
//                cartItemRepository.save(userItem);
//
//                cartItemRepository.deleteById(guestItem.getId());
//
//            } else {
//                // PRODUCT ONLY IN GUEST CART → MOVE
//                CartItem newItem = new CartItem();
//                newItem.setCartId(userCart.getId());
//                newItem.setProductId(guestItem.getProductId());
//                newItem.setQuantity(guestItem.getQuantity());
//                newItem.setPrice(guestItem.getQuantity()*90.0);
//                cartItemRepository.save(newItem);
//                cartItemRepository.deleteById(guestItem.getId());
//            }
//        }
//
//        cartRepository.deleteById(guestCart.getId());
//    }

    @Transactional
    public MergeGuestToUserCartResponse mergeGuestCartToUserCart(String guestUserId,String userId){
        Optional<Cart> guestCartOpt = cartRepository.findByGuestUserId(guestUserId);
        Optional<Cart> userCartOpt  = cartRepository.findByGuestUserId(userId);
        System.out.println("guest user cart "+guestCartOpt);
        System.out.println("usert cart "+userCartOpt);
        if (!guestCartOpt.isPresent()) return new MergeGuestToUserCartResponse(false,false,true);

        Cart guestCart = guestCartOpt.get();

        // Case 1: user has no cart → convert
        if (!userCartOpt.isPresent()) {
            guestCart.setGuestUserId(userId);
            cartRepository.save(guestCart);
            if(!cartItemRepository.findByGuestUserId(guestUserId).isEmpty()){
                cartItemRepository.updateOwner(guestCart.getId(), userId);
            }

//            cartItemRepository.updateOwner(guestCart.getId(), userId);
            return new MergeGuestToUserCartResponse(false,true,true);
        }

        // Case 2: merge
        Cart userCart = userCartOpt.get();

        for (CartItem guestItem : cartItemRepository.findByCartId(guestCart.getId())) {
            System.out.println("Inside "+guestItem);
            CartItem userItem=cartItemRepository.findByCartIdAndProductId(
                    userCart.getId(), guestItem.getProductId()
            ).orElse(null);

            if (userItem != null) {
                // SAME PRODUCT → MERGE QTY
                userItem.setQuantity(
                        userItem.getQuantity() + guestItem.getQuantity()
                );

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
        return new MergeGuestToUserCartResponse(true,true,true);
    }


    public CartItemResponse getCartPageAllCartItems(String cartId, String guestUserId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new NullPointerException("no data found"));

        if (!cart.getGuestUserId().equals(guestUserId)) {
            throw new RuntimeException("Unauthorized");
        }

        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        Map<String,String> productWithIdQty=items.stream().collect(Collectors.toMap(CartItem::getProductId, item->item.getId()+","+item.getQuantity(),(olditem, newitem)->olditem, LinkedHashMap::new));

        String productIds=items.stream().map(CartItem::getProductId).collect(Collectors.joining(","));

        List<ProductResponseCart> productResponseCarts=productClient.getListProductResponse(productIds).getBody();

        double gstRate=0.09;
        Double deliverCharge=20.0;
        List<CartProductResponse> cartProductResponseList=productResponseCarts.stream().map(productResponseCart ->{
          String []idWIthQty=productWithIdQty.get(productResponseCart.getId()).split(",");
          return new CartProductResponse(idWIthQty[0],Integer.parseInt(idWIthQty[1]),productResponseCart.getId(),productResponseCart.getProductName(),productResponseCart.getProductPrice(),productResponseCart.getProductDiscountPrice(),productResponseCart.getImage(),productResponseCart.getImageName());
//          return cartProductResponse;
        } ).collect(Collectors.toList());

        double totalPrice=cartProductResponseList.stream().mapToDouble(cartProductResponse->Double.parseDouble(String.valueOf(cartProductResponse.getQuantity()))*cartProductResponse.getProductDiscountPrice()).sum();
        Double gstCharge=totalPrice*gstRate;
        Double totalPriceAFterReduction=null;
        if(totalPrice>(gstCharge+deliverCharge)){
            totalPriceAFterReduction=totalPrice-(gstCharge+deliverCharge);
        }
        else{
            throw new RuntimeException("Total price is high than delivery charge and gst charge");
        }

        return new CartItemResponse(cartProductResponseList,totalPrice,gstCharge,deliverCharge,totalPriceAFterReduction,gstRate);


    }

    /* ---------- CLEAR AFTER ORDER ---------- */
    public void clearCart(String userKey) {
        cartRepository.findByGuestUserId(userKey)
                .ifPresent(c -> cartItemRepository.deleteByCartId(c.getId()));
    }
    //    @Override
//    public CartItem addToCart(String guestUserCartId, CartItemRequest cartItemRequest){
//        Cart cart=cartRepository.findByGuestUserId(guestUserCartId).orElseGet(()->null);
//        if(cart==null){
//            Cart c = new Cart(); c.setId(UUID.randomUUID().toString()); c.setGuestUserId(guestUserCartId); c.setCreatedDate(System.currentTimeMillis());
//            Cart cartSave= cartRepository.save(c);
//            CartItem i = new CartItem();
//            i.setId(UUID.randomUUID().toString());
//            i.setCartId(cartSave.getId());
//            i.setProductId(cartItemRequest.getProductId());
//            i.setQuantity(cartItemRequest.getQuantity());
//            i.setCreatedDate(System.currentTimeMillis());
//            i.setGuestUserId(guestUserCartId);
//            //call price by using productid
//            i.setPrice(cartItemRequest.getQuantity()*90.0);
//            return cartItemRepository.save(i);
//        }
//
//
//        CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(), cartItemRequest.getProductId()).orElseGet(() ->null);
//
////        assert cartItem != null;
//        if(cartItem!=null){
//            cartItem.setQuantity(cartItem.getQuantity() + cartItem.getQuantity());
//            //call api of price by productid
//            cartItem.setPrice((cartItem.getQuantity() + cartItem.getQuantity())*cartItem.getPrice());
//            return cartItemRepository.save(cartItem);
//        }
//
//        CartItem i = new CartItem();
//        i.setId(UUID.randomUUID().toString());
//        i.setCartId(cart.getId());
//        i.setProductId(cartItemRequest.getProductId());
//        i.setQuantity(cartItemRequest.getQuantity());
//        i.setCreatedDate(System.currentTimeMillis());
//        //call price by using productid
//        i.setPrice(cartItemRequest.getQuantity()*90.0);
//        i.setGuestUserId(guestUserCartId);
//        return cartItemRepository.save(i);
//
//    }
}
