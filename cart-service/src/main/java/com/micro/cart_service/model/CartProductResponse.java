package com.micro.cart_service.model;

public class CartProductResponse {

    private String cartItemId;
    private Integer quantity;
    private String productId;
    private String productName;
    private Double productPrice;
    private Double productDiscountPrice;
    private byte[] image;
    private String imageName; // Image stored using URL

    public CartProductResponse(String cartItemId, Integer quantity, String productId, String productName, Double productPrice, Double productDiscountPrice, byte[] image, String imageName) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.image = image;
        this.imageName = imageName;
    }

    public CartProductResponse() {
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(Double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
