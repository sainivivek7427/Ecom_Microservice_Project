package com.micro.product.client;

import com.micro.product.dto.SubCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "subcategory-service")
public interface SubCategoryClient {

    @GetMapping("/subcategories/by-id")
    public ResponseEntity<SubCategory> getSubCategoryById(@RequestParam("sid")  String sid);
    }
