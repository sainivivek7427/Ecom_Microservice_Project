package com.micro.category.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "id", nullable = false)
    private String id;  // UUID as String

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private long createdDate; // epoch time

    @Column(name = "image_name", length = 1000)
    private String imageName; // Image stored using URL (like Google Drive)

    @Lob // Indicates that this field should be stored as a Large Object
    @Column(name = "image_data") // For MySQL
//     @Column(name = "image_data", columnDefinition = "BYTEA") // For PostgreSQL
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "updated_date")
    private Long updatedDate;

    // Constructors
    public Category() {}

//    public Category(String id, String name, long createdDate) {
//        this.id = id;
//        this.name = name;
//        this.createdDate = createdDate;
//    }


    public Category(String id, String name, long createdDate, String imageName, byte[] image, Long updatedDate) {
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
