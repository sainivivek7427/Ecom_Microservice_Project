package com.micro.cart_service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(length = 100)
    private String id; // cart id
    @Column(name = "guest_user_id", length = 100)
    private String guestUserId; // before login guestId, after login userId
    private Long createdDate;

    public Cart(String id, String guestUserId, Long createdDate) {

        this.id = id;
        this.guestUserId = guestUserId;
        this.createdDate = createdDate;
    }

    public Cart() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(String guestUserId) {
        this.guestUserId = guestUserId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", guestUserId='" + guestUserId + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}