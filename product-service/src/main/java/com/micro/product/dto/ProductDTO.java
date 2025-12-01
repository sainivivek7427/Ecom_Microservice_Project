package com.micro.product.dto;

import java.util.Arrays;

public class ProductDTO {

    private String id;
    private String name;//
    private String description;//
    private Double price;//
    private Double discountPercent;//
    private Double discountPrice;
    private String brand;//
    private Integer stockQuantity;//
    private String imageName; // Image stored using URL
    private byte[] image;
    private String categoryId; // Only category ID stored
    private String categoryName; // Name used to get category via Feign
    private String subcategoryId; // Only subcategory ID stored
    private Boolean isActive = true;
    private Long createdDate;
    private Long updatedDate;

    // === Getters & Setters ===

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }
    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
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
        return "ProductDTO{" +
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
                ", categoryName='" + categoryName + '\'' +
                ", subcategoryId='" + subcategoryId + '\'' +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
