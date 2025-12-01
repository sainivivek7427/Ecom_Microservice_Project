package com.micro.product.dto;


import java.util.Arrays;

public class CategoryDTO {

    private String id;  // UUID as String

    private String name;

    private long createdDate; // epoch time

    private String imageName; // Image stored using URL (like Google Drive)

    private byte[] image;

    private Long updatedDate;

    // Constructors
    public CategoryDTO() {}

//    public Category(String id, String name, long createdDate) {
//        this.id = id;
//        this.name = name;
//        this.createdDate = createdDate;
//    }


    public CategoryDTO(String id, String name, long createdDate, String imageName, byte[] image, Long updatedDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.imageName = imageName;
        this.image = image;
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Getters & Setters
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

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
