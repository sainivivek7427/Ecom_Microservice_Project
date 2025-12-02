package com.micro.product.repository;

import com.micro.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByCategoryId(String categoryId);

    // 1. Get products by discount percent
    List<Product> findByDiscountPercent(Double discountPercent);

    // 2. Get products by category ID (we'll use category name and convert to ID in service)
    List<Product> findByCategoryId(String categoryId);

    // 3. Get new arrival products based on createdDate >= passed epoch
    List<Product> findByCreatedDateGreaterThanEqual(Long createdDateEpoch);

    // 4. Show Product today hot deals
    List<Product> findByCreatedDateBetween(Long startOfDay, Long endOfDay);

    @Query("SELECT p FROM Product p WHERE p.discountPercent >= :min AND p.discountPercent < :max")
    List<Product> findByDiscountRange(@Param("min") double min, @Param("max") double max);

    List<Product> findBySubcategoryId(String subcategoryid);

//    List<Product> findByCategoryId(String categoryId);


}
