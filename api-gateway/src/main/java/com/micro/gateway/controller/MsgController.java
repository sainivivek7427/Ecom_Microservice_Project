package com.micro.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

    @GetMapping("/api-gateway")
    public String msgController()
    {
        return "Satyam Raikwar";
    }
}
