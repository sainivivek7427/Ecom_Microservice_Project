package com.micro.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/{id}")   // changed to product
    public String get(@PathVariable String id){
        return "Product:"+id;
    }

    @GetMapping()
    public String msgController()
    {
        return "Product Service Running";
    }

    @GetMapping("/msg")
    public String msgControllerProduct()
    {
        return "Product Service Running msg ";
    }

}
