package com.micro.subcategory.repository;
import com.micro.subcategory.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository  extends JpaRepository<SubCategory,String> {
    Optional<SubCategory> findByScategory(String scategory);
    List<SubCategory> findByCategoryid(String categoryid);


}
