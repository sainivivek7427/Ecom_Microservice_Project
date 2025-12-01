package com.micro.category.client;

import com.micro.category.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/by-category/{categoryId}")
    List<ProductDTO> getProductsByCategoryId(@PathVariable("categoryId") String categoryId);

    @GetMapping("/product/check-category/{categoryId}")
    boolean checkCategoryUsed(@PathVariable("categoryId") String categoryId);
}
