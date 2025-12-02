package com.micro.subcategory.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.util.Arrays;

@Entity
public class SubCategory {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "scategory")
    private String scategory;

    @Column(name = "image_name", length = 1000)
    private String imageName; // Image stored using URL (like Google Drive)

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name="category_id")
    private String categoryid;

    private Long createdate;

    @Column(name = "updated_date")
    private Long updatedDate;


    public SubCategory(String id, String scategory, String imageName, byte[] image, String categoryid, Long createdate, Long updatedDate) {
        this.id = id;
        this.scategory = scategory;
        this.imageName = imageName;
        this.image = image;
        this.categoryid = categoryid;
        this.createdate = createdate;
        this.updatedDate = updatedDate;
    }

    public SubCategory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScategory() {
        return scategory;
    }

    public void setScategory(String scategory) {
        this.scategory = scategory;
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

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", scategory='" + scategory + '\'' +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", categoryid='" + categoryid + '\'' +
                ", createdate=" + createdate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
