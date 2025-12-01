package com.micro.category.serviceimpl;

import com.micro.category.client.ProductClient;
import com.micro.category.dto.ProductDTO;
import com.micro.category.entity.Category;
import com.micro.category.repository.CategoryRepository;
import com.micro.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public Category createCategory(String categoryname, MultipartFile categoryimage) {
        try {
            Category category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setName(categoryname);
            category.setImageName(categoryimage.getOriginalFilename());
            category.setImage(categoryimage.getBytes());
            category.setCreatedDate(System.currentTimeMillis());
            category.setUpdatedDate(System.currentTimeMillis());
            return categoryRepository.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Error saving category image", e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Category updateCategory(String cid, String categoryname, MultipartFile categoryimage) {
        try {
            Category category = getCategoryById(cid);
            category.setName(categoryname);
            if (categoryimage != null && !categoryimage.isEmpty()) {
                category.setImage(categoryimage.getBytes());
                category.setImageName(categoryimage.getOriginalFilename());
            }
            category.setUpdatedDate(System.currentTimeMillis());
            return categoryRepository.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Error updating category image", e);
        }
    }

    @Override
    public String deleteCategory(String id) {
        boolean isUsed = productClient.checkCategoryUsed(id); // call Product service
        if (isUsed) {
            throw new RuntimeException("⚠ Cannot delete: Category is used in products");
        }
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        return productClient.getProductsByCategoryId(category.getId());
    }
    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow(()-> new NullPointerException("Not Category Found"));
    }
}
