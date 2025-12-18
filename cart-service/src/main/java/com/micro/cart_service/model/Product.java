package com.micro.cart_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public class Product {

    private String id;

    private String name;

    private String description;

    private Double price;

    private Double discountPercent;
    private Double discountPrice;
    private String brand;
    private Integer stockQuantity;

    private String imageName;


    private byte[] image;

    private String categoryId;
    private String subcategoryId;

    private Boolean isActive = true;

    private Long createdDate;
    private Long updatedDate;

}
