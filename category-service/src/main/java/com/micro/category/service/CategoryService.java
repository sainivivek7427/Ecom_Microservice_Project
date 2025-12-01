package com.micro.category.service;

import com.micro.category.dto.ProductDTO;
import com.micro.category.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    Category createCategory(String categoryname, MultipartFile categoryimage);

    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category updateCategory(String cid, String categoryname, MultipartFile categoryimage);

    String deleteCategory(String id);

    List<ProductDTO> getProductsByCategoryName(String categoryName);
}
