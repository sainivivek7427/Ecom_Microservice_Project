package com.micro.subcategory.service;

import com.micro.subcategory.entity.SubCategory;
import com.micro.subcategory.model.SubCategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface SubCategoryService {
    public SubCategory createSubCategory(String subcategoryname, MultipartFile file, String categoryid);

    public List<SubCategory> getAllSubCategory();

    public SubCategory updateSubCategorybyId(String subid,String subcategoryname,MultipartFile file);

    public String deleteSubCategory(String subid);

    public List<SubCategoryResponse> getSubcategoriesByCategory(String categoryid);
}
