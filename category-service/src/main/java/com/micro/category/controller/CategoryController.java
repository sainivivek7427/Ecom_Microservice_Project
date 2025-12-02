package com.micro.category.controller;

import com.micro.category.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.micro.category.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    private final CategoryService categoryServices;

    @Autowired
    public CategoryController(CategoryService categoryServices) {
        this.categoryServices = categoryServices;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestParam("categoryname") String categoryname,
                                            @RequestParam("categoryimage") MultipartFile categoryimage) {
        Category response = categoryServices.createCategory(categoryname,categoryimage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryServices.getAllCategories());
    }

    @GetMapping("/get/{cid}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("cid") String cid) {
        return ResponseEntity.ok(categoryServices.getCategoryById(cid));
    }

    @PutMapping("/update/{cid}")
    public ResponseEntity<?> updateCategoryName(@PathVariable String cid,@RequestParam("categoryname") String categoryname,
                                                @RequestParam("categoryimage") MultipartFile categoryimage) {
        return ResponseEntity.ok(categoryServices.updateCategory(cid, categoryname,categoryimage));
    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "cid") String cid) {
        return ResponseEntity.ok(categoryServices.deleteCategory(cid));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<Category> getCategoryByName(@RequestParam("name") String name){
        System.out.println("name "+name);
        Category categoryByName=categoryServices.getCategoryByName(name);
        System.out.println("category name "+categoryByName);
        return ResponseEntity.ok(categoryByName);
    }
}