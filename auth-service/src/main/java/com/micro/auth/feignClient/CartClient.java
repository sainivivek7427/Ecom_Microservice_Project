package com.micro.auth.feignClient;


import com.micro.auth.model.MergeGuestToUserCartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/cart/merge/guest-user/{userId}")
    public ResponseEntity<MergeGuestToUserCartResponse> mergeGuestToUserCart(@PathVariable("userId") String userId, @RequestParam("guestUserId") String guestUserId);

    }
