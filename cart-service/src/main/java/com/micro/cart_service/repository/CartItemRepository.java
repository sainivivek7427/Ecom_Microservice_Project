package com.micro.cart_service.repository;

import com.micro.cart_service.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByCartId(String cartId);
    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);

    List<CartItem> findByGuestUserId(String guestUserId);


//    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);

//    List<CartItem> findByCartId(String cartId);

    void deleteByCartId(String cartId);

    @Modifying
    @Query("update CartItem c set c.guestUserId = :owner where c.cartId = :cartId")
    void updateOwner(@Param("cartId") String cartId,
                     @Param("owner") String owner);

    Optional<CartItem> findByIdAndCartId(String id, String cartId);

    void deleteByIdAndCartId(String id, String cartId);
}
