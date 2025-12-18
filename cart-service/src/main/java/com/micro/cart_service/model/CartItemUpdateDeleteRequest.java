package com.micro.cart_service.model;

public class CartItemUpdateDeleteRequest {

    private Integer quantity;
    private String guestUserId;

    private String cartId;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(String guestUserId) {
        this.guestUserId = guestUserId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "CartItemUpdateDeleteRequest{" +
                "quantity=" + quantity +
                ", guestUserId='" + guestUserId + '\'' +
                ", cartId='" + cartId + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
