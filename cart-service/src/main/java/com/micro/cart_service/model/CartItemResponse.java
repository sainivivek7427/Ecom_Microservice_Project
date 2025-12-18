package com.micro.cart_service.model;

import java.util.List;

public class CartItemResponse {
    private List<CartProductResponse> cartProductResponseList;
    private Double total;
    private Double gstcharges;
    private Double deliveryCharges;
    private Double totalAfterReduction;
    private Double gstRate;

    public CartItemResponse(List<CartProductResponse> cartProductResponseList, Double total, Double gstcharges, Double deliveryCharges, Double totalAfterReduction, Double gstRate) {
        this.cartProductResponseList = cartProductResponseList;
        this.total = total;
        this.gstcharges = gstcharges;
        this.deliveryCharges = deliveryCharges;
        this.totalAfterReduction = totalAfterReduction;
        this.gstRate = gstRate;
    }

    public CartItemResponse() {
    }

    public List<CartProductResponse> getCartProductResponseList() {
        return cartProductResponseList;
    }

    public void setCartProductResponseList(List<CartProductResponse> cartProductResponseList) {
        this.cartProductResponseList = cartProductResponseList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getGstRate() {
        return gstRate;
    }

    public void setGstRate(Double gstRate) {
        this.gstRate = gstRate;
    }

    public Double getGstcharges() {
        return gstcharges;
    }

    public void setGstcharges(Double gstcharges) {
        this.gstcharges = gstcharges;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Double getTotalAfterReduction() {
        return totalAfterReduction;
    }

    public void setTotalAfterReduction(Double totalAfterReduction) {
        this.totalAfterReduction = totalAfterReduction;
    }
}
