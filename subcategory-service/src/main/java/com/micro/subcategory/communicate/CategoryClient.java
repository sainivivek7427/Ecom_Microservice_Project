package com.micro.subcategory.communicate;

import com.micro.subcategory.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/categories/get/{cid}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("cid") String cid);
}