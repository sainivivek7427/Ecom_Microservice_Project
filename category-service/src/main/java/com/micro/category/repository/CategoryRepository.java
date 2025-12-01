package com.micro.category.repository;

import com.micro.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    //Category findByName(String name);
    Optional<Category> findByName(String name);
}



//public interface CategoryRepository extends JpaRepository<Category, String> {
//
//
//    //Category findByName(String name);
//    Optional<Category> findByName(String name);
//
//}
