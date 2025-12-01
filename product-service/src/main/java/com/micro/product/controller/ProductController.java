package com.micro.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.product.client.CategoryClient;
import com.micro.product.dto.CategoryDTO;
import com.micro.product.dto.ProductDTO;
import com.micro.product.entity.Product;
import com.micro.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryClient categoryClient;

    // ------------------- Add Product -------------------
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestPart("productdata") String productData,
            @RequestPart("productimage") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO productDTO = mapper.readValue(productData, ProductDTO.class);

            // Call Category Service to get Category ID
            CategoryDTO category = categoryClient.getCategoryByName(productDTO.getCategoryName());
            if (category == null) {
                return ResponseEntity.badRequest().body("Category Not Found!");
            }

            // Map ProductDTO to Product entity
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setDiscountPercent(productDTO.getDiscountPercent());
            product.setDiscountPrice(productDTO.getPrice() -
                    (productDTO.getPrice() * productDTO.getDiscountPercent()) / 100);
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setCategoryId(category.getId());
            product.setSubcategoryId(productDTO.getSubcategoryId());
            product.setActive(true);
            product.setCreatedDate(System.currentTimeMillis());
            product.setUpdatedDate(System.currentTimeMillis());
            product.setImage(file.getBytes());
            product.setImageName(file.getOriginalFilename());

            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.ok(savedProduct);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ------------------- Get All Products -------------------
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ------------------- Get Product By ID -------------------
    @GetMapping("/get/{pid}")
    public ResponseEntity<?> getProductById(@PathVariable String pid) {
        try {
            Product product = productService.getProductById(pid);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ------------------- Delete Product -------------------
    @DeleteMapping("/delete/{pid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String pid) {
        try {
            productService.deleteProductById(pid); // Match the method in ProductService
            return ResponseEntity.ok("Product Deleted Successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ------------------- Get Products By Category ID -------------------
    @GetMapping("/by-categoryid")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@RequestParam String categoryid) {
        List<Product> products = productService.getProductsByCategoryId(categoryid);
        return ResponseEntity.ok(products);
    }

    // ------------------- Get Products By Category Name -------------------
    @GetMapping("/by-category")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@RequestParam String categoryName) {
        // Use CategoryClient to get category ID first
        CategoryDTO category = categoryClient.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Product> products = productService.getProductsByCategoryId(category.getId());
        return ResponseEntity.ok(products);
    }

    // ------------------- Get Products By Discount -------------------
    @GetMapping("/by-discount")
    public List<Product> getProductsByDiscount(@RequestParam Double discountPercent) {
        return productService.getProductsByDiscount(discountPercent);
    }

    // ------------------- Get New Arrivals -------------------
    @GetMapping("/new-arrivals")
    public List<Product> getNewArrivals(@RequestParam Long fromEpoch) {
        return productService.getNewArrivalProducts(fromEpoch);
    }

    // ------------------- Get Today Hot Products -------------------
    @GetMapping("/today-hot")
    public List<Product> getTodayHotProducts() {
        return productService.getTodayHotProducts();
    }

    // ------------------- Get Products By Discount Range -------------------
    @GetMapping("/discount/{min}/{max}")
    public List<Product> getProductByDiscountRange(@PathVariable double min, @PathVariable double max) {
        return productService.getProductsByDiscountRange(min, max);
    }
}
