package com.micro.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.product.client.CategoryClient;
import com.micro.product.client.SubCategoryClient;
import com.micro.product.dto.Category;
import com.micro.product.dto.ProductDTO;
import com.micro.product.dto.SubCategory;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SubCategoryClient subCategoryClient;

    // ------------------- Add Product -------------------
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestPart("productdata") String productData,
            @RequestPart("productimage") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO productDTO = mapper.readValue(productData, ProductDTO.class);
            System.out.println("Prdouct dto "+productDTO);

            // Call Category Service to get Category ID
            Category category = categoryClient.getCategoryByName(productDTO.getCategoryName()).getBody();
            System.out.println("category "+category);
            if (category == null) {
                return ResponseEntity.badRequest().body("Category Data Not Found!");
            }

            SubCategory subCategory = subCategoryClient.getSubCategoryById(productDTO.getSubcategoryId()).getBody();
            System.out.println("subcategory "+subCategory);
            if (subCategory == null) {
                return ResponseEntity.badRequest().body("subCategory Data Not Found!");
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


    @PostMapping("/import-csv")
    public ResponseEntity<?> importProducts(@RequestParam("file") MultipartFile file) {

        try {
            productService.importProductsFromCsv(file);
            return ResponseEntity.ok("CSV Imported Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ------------------- Get All Products -------------------
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ------------------- Get Product By ID -------------------
    @GetMapping("/get/{pid}")
    public ResponseEntity<?> getProductById(@PathVariable("pid") String pid) {
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
    public ResponseEntity<List<Product>> getProductsByCategoryId(@RequestParam(name = "categoryid") String categoryid) {
        List<Product> products = productService.getProductsByCategoryId(categoryid.trim());
        return ResponseEntity.ok(products);
    }
    // ------------------- Get Products By Category Name -------------------
    @GetMapping("/by-category")
    public ResponseEntity<List<Product>> getProductsByCategoryName(
            @RequestParam("categoryName") String categoryName) {
        Category category = categoryClient.getCategoryByName(categoryName).getBody();
        if (category == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Product> products = productService.getProductsByCategoryId(category.getId());
        return ResponseEntity.ok(products);
    }


    // ------------------- Get Products By Discount -------------------
    @GetMapping("/by-discount")
    public List<Product> getProductsByDiscount(
            @RequestParam("discountPercent") Double discountPercent) {
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
    public List<Product> getProductByDiscountRange(
            @PathVariable("min") double min,
            @PathVariable("max") double max) {
        return productService.getProductsByDiscountRange(min, max);
    }


    @GetMapping("/check-category/{categoryId}")
    ResponseEntity<Boolean> checkCategoryUsed(@PathVariable("categoryId") String categoryId){
        Boolean checkCategorIdExist=productService.getCategoryExist(categoryId);
        return ResponseEntity.ok(checkCategorIdExist);
    }

}
