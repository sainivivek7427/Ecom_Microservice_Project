package com.micro.cart_service.Entity;
import jakarta.persistence.*;


@Entity
@Table(name = "cart_items")

public class CartItem {
    @Id
    @Column(length = 100)
    private String id;
    @Column(name="cart_id", length = 100)
    private String cartId;
    @Column(name="product_id", length = 100)
    private String productId;

    private Double price;
    private Integer quantity;
    private Long createdDate;
    private String guestUserId;


    public CartItem() {

    }

    public CartItem(String id, String cartId, String productId, Double price, Integer quantity, Long createdDate, String guestUserId) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.guestUserId = guestUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(String guestUserId) {
        this.guestUserId = guestUserId;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", cartId='" + cartId + '\'' +
                ", productId='" + productId + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", createdDate=" + createdDate +
                ", guestUserId='" + guestUserId + '\'' +
                '}';
    }
}

