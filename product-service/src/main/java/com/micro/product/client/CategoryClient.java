package com.micro.product.client;

import com.micro.product.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/api/categories/get-by-name")
    CategoryDTO getCategoryByName(@RequestParam("name") String name);
}
