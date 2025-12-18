package com.micro.cart_service.model;

import java.util.Arrays;

public class ProductResponseCart {

    private String id;
    private String productName;
    private Double productPrice;
    private Double productDiscountPrice;
    private byte[] image;
    private String imageName; // Image stored using URL

    public ProductResponseCart() {
    }

    public ProductResponseCart(String id, String productName, Double productPrice, Double productDiscountPrice, byte[] image, String imageName) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.image = image;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductResponseCart{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDiscountPrice='" + productDiscountPrice + '\'' +
                ", image=" + Arrays.toString(image) +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
