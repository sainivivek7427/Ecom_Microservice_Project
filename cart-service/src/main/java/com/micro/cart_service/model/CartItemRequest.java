package com.micro.cart_service.model;

public class CartItemRequest {
    private String productId;
    private Integer quantity;
    private String guestUserId;

    // Getters and Setters


    public String getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(String guestUserId) {
        this.guestUserId = guestUserId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemRequest{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", guestUserId='" + guestUserId + '\'' +
                '}';
    }
}
