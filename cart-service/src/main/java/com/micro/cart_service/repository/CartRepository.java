package com.micro.cart_service.repository;

import com.micro.cart_service.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByGuestUserId(String guestUserId);
//    Cart existsByGuestUserId(String guestUserId);
}
