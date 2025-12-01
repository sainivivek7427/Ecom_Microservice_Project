package com.micro.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/categories/get-by-name")
    public ResponseEntity<?> getCategoryByName(@RequestParam("name") String name);
}
