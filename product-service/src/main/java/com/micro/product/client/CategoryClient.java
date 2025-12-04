package com.micro.product.client;

import com.micro.product.dto.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/categories/get-by-name")
    public ResponseEntity<Category> getCategoryByName(@RequestParam("name") String name);

    @GetMapping("/categories/get/{cid}")
    Category getCategoryById(@PathVariable("cid") String cid);
}
