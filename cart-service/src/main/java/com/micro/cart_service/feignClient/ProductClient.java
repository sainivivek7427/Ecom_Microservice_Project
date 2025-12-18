package com.micro.cart_service.feignClient;

import com.micro.cart_service.model.ProductResponseCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

//    @GetMapping("/products//get/{pid}")
//    public ResponseEntity<Product> getProductById(@PathVariable("pid") String pid);

    @GetMapping("/products/by-pids")
    public ResponseEntity<List<ProductResponseCart>> getListProductResponse(@RequestParam("productIds") String productIds);

    @GetMapping("/products/stock/check")
    public ResponseEntity<Boolean> checkStock(@RequestParam("productId") String productId, @RequestParam("requestedQty") Integer requestedQty);

    }
