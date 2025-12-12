package com.micro.product.entity;


import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "products")
public class Product {
//
//    @Id
//    @Column(name = "id", columnDefinition = "VARCHAR(36)")
//    private String id;
//
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "description", length = 1000)
//    private String description;
//
//    @Column(name = "price", nullable = false)
//    private Double price;
//
//    @Column(name = "discount_percent")
//    private Double discountPercent;
//
//    @Column(name = "discount_price")
//    private Double discountPrice;
//
//    @Column(name = "brand")
//    private String brand;
//
//    @Column(name = "stock_quantity")
//    private Integer stockQuantity;
//
//    @Column(name = "image_name", length = 1000)
//    private String imageName; // Image stored using URL (like Google Drive)
//
//
//    @Lob // Indicates that this field should be stored as a Large Object
//    @Column(name = "image_data", columnDefinition = "LONGBLOB") // For MySQL
//    private byte[] image;
//
//    @Column(name = "category_id")
//    private String categoryId; // Only category ID stored
//
//
//
//    @Column(name = "subcategory_id")
//    private String subcategoryId; // Only category ID stored
//
//    @Column(name = "is_active")
//    private Boolean isActive = true;
//
//    @Column(name = "created_date")
//    private Long createdDate;
//
//    @Column(name = "updated_date")
//    private Long updatedDate;

    @Id
    @Column(name = "id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    private Double discountPercent;
    private Double discountPrice;
    private String brand;
    private Integer stockQuantity;

    @Column(name = "image_name", length = 1000)
    private String imageName;

    @Lob
    @Column(name = "image_data")
    private byte[] image;

    private String categoryId;
    private String subcategoryId;

    private Boolean isActive = true;

    private Long createdDate;
    private Long updatedDate;

    // === Getters & Setters ===

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }



    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPercent=" + discountPercent +
                ", discountPrice=" + discountPrice +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", categoryId='" + categoryId + '\'' +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
