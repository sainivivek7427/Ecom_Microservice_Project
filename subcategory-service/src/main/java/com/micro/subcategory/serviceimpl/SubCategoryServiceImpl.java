package com.micro.subcategory.serviceimpl;



import com.micro.subcategory.communicate.CategoryClient;
import com.micro.subcategory.entity.SubCategory;
import com.micro.subcategory.model.Category;
import com.micro.subcategory.model.SubCategoryResponse;
import com.micro.subcategory.repository.SubCategoryRepository;
import com.micro.subcategory.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl  implements SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    CategoryClient categoryClient;
    @Override
    public SubCategory createSubCategory(String subcategoryname, MultipartFile file, String categoryid){
        try{
            SubCategory subCategory=new SubCategory();

            //call third part service
            Category category=categoryClient.getCategoryById(categoryid).getBody();
            if(category==null){
                throw new NullPointerException("Category not found");
            }
//            System.out.println("category "+category);
            subCategory.setCategoryid(category.getId());
            subCategory.setId(UUID.randomUUID().toString());
            subCategory.setScategory(subcategoryname);
            subCategory.setImageName(file.getOriginalFilename());
            subCategory.setImage(file.getBytes());
            subCategory.setCreatedate(System.currentTimeMillis());
            subCategory.setUpdatedDate(System.currentTimeMillis());
            return subCategoryRepository.save(subCategory);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<SubCategory> getAllSubCategory(){
        return subCategoryRepository.findAll();
    }

    @Override
    public SubCategory updateSubCategorybyId(String subid,String subcategoryname,MultipartFile file){
        try{
            SubCategory subCategory=subCategoryRepository.findById(subid).orElseThrow(()->new NullPointerException("Null data found"));
            subCategory.setImage(file.getBytes());
            subCategory.setId(subCategory.getId());
            subCategory.setImageName(file.getOriginalFilename());
            subCategory.setCategoryid(subCategory.getCategoryid());
            subCategory.setCreatedate(subCategory.getCreatedate());
            subCategory.setScategory(subcategoryname);
            subCategory.setUpdatedDate(System.currentTimeMillis());
            return subCategoryRepository.save(subCategory);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String deleteSubCategory(String subid){
        SubCategory subCategory=subCategoryRepository.findById(subid).orElseThrow(()->new NullPointerException("Null data found"));
        subCategoryRepository.delete(subCategory);
        return "Delete successfully subcategory";
    }

    public List<SubCategoryResponse> getSubcategoriesByCategory(String categoryid) {
        List<SubCategory> subcategories = subCategoryRepository.findByCategoryid(categoryid);
        return subcategories.stream()
                .map(sub -> new SubCategoryResponse(sub.getId(), sub.getScategory()))
                .collect(Collectors.toList());
    }
}
