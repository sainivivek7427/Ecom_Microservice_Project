package com.micro.subcategory.controller;

import com.micro.subcategory.entity.SubCategory;
import com.micro.subcategory.model.SubCategoryResponse;
import com.micro.subcategory.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    @GetMapping("")
    public ResponseEntity<?> getResponse(){
        System.out.println("Success");
        return ResponseEntity.ok("Success , working now subcategory service");
    }

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createSubCategory(@RequestParam("subcategoryname") String subcategoryname,
                                               @RequestParam("subcategoryimage") MultipartFile file,
                                               @RequestParam String categoryid){
        System.out.println("subcategoryname "+subcategoryname+"categoryid "+categoryid);
        SubCategory subCategory=subCategoryService.createSubCategory(subcategoryname,file,categoryid);
        return ResponseEntity.ok(subCategory);

    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllSubcategory(){
        List<SubCategory> subCategoryList=subCategoryService.getAllSubCategory();
        return  ResponseEntity.ok(subCategoryList);
    }

    @PutMapping("/{subid}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable String subid,@RequestParam("subcategoryname") String subcategoryname,
                                                @RequestParam("subcategoryimage") MultipartFile file){
        SubCategory updatesubCategory=subCategoryService.updateSubCategorybyId(subid,subcategoryname,file);
        return ResponseEntity.ok(updatesubCategory);
    }

    @DeleteMapping("/{subid}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable String subid){
        String deleteSubCategoryResponse=subCategoryService.deleteSubCategory(subid);
        return ResponseEntity.ok(deleteSubCategoryResponse);
    }
    //use for fetch liat of subcategory by categoryid in add product form
    //Get subcategory name,subcategoryid  by categoryid
    @GetMapping("/by-category")
    public List<SubCategoryResponse> getSubcategoriesByCategory(@RequestParam String categoryId) {
        return subCategoryService.getSubcategoriesByCategory(categoryId);
    }

    @GetMapping("/by-id")
    public ResponseEntity<SubCategory> getSubCategoryById(@RequestParam("sid")  String sid){
        SubCategory subCategoryRes=subCategoryService.getSubCategoryBySId(sid);
        return ResponseEntity.ok(subCategoryRes);

    }


    //First categoryid pass in request param
    //secodn create dto classs=> subcategoryResposneDto private String subctrname,subid
    //return dtoResponseclass in this api


}
