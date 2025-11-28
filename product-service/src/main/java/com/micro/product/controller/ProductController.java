package com.micro.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product/{id}")   // changed to product
    public String get(@PathVariable String id){
        return "Product:"+id;
    }

    @GetMapping("/product")
    public String msgController()
    {
        return "Product Service Running";
    }
}
