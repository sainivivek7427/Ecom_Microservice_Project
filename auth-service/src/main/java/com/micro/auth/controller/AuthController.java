package com.micro.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AuthController {

    @PostMapping("/auth/login")
    public Map<String,String> login(@RequestBody Map<String,String> req) {
        Map<String, String> stringStringMap = new java.util.HashMap<>();
        stringStringMap.put("token", "hkjs");
        return stringStringMap;
    }
}
