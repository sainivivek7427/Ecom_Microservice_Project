package com.micro.auth.controller;

import com.micro.auth.config.JwtUtil;
import com.micro.auth.config.UserDetailServiceimpl;
import com.micro.auth.entity.Customer;
import com.micro.auth.exception.LoginException;
import com.micro.auth.exception.UserNotFoundException;
import com.micro.auth.feignClient.CartClient;
import com.micro.auth.model.AuthRequest;
import com.micro.auth.model.MergeGuestToUserCartResponse;
import com.micro.auth.model.MessageResponseDto;
import com.micro.auth.repository.CustomerRepository;
import com.micro.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired private UserDetailServiceimpl userDetailsService;
    @Autowired private JwtUtil jwtUtil;

//    @Autowired
//    JwtAuthenticationFilter authenticationFilter;

    @Autowired
    AuthService authService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CartClient cartClient;

    @PostMapping("/reg")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        Customer customerResponse=authService.registerCustomer(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            System.out.println("Login Started...");

            Customer customer;

            if (request.getTypeFormat().equalsIgnoreCase("sms")) {

                Long phoneno = Long.parseLong(request.getTypeValue());

                customer = customerRepository.findByPhonenoAndPassword(phoneno, request.getPassword())
                        .orElseThrow(() -> new UserNotFoundException("User not Found with Phone Number and Password"));
            } else if (request.getTypeFormat().equalsIgnoreCase("email")) {

                customer = customerRepository
                        .findByUsername(request.getTypeValue())
                        .orElseThrow(() -> new UserNotFoundException("User Not Found with Username and Password"));

            } else {
                throw new LoginException("Invalid login type format must be: sms or email",HttpStatus.UNAUTHORIZED);
            }

            System.out.println("Username: " + customer.getUsername());
            System.out.println("Password entered: " + request.getPassword());

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customer.getUsername(), request.getPassword())
            );

            UserDetails user = (UserDetails) auth.getPrincipal();

            MergeGuestToUserCartResponse mergeGuestToUserCartResponse=cartClient.mergeGuestToUserCart("user:"+customer.getCuuid(), request.getGuestUserId()).getBody();
            if (mergeGuestToUserCartResponse != null && mergeGuestToUserCartResponse.isSuccess()) {
                System.out.println("merge "+mergeGuestToUserCartResponse.isExistGuestCart()+" user cart "+mergeGuestToUserCartResponse.isExistUserCart() +" success "+mergeGuestToUserCartResponse.isSuccess());
                String accessToken = jwtUtil.generateAccessToken(user);
                String refreshToken = jwtUtil.generateRefreshToken(user);

                Map<String, String> result = new HashMap<>();
                result.put("token", accessToken);
                result.put("refreshToken", refreshToken);

                return ResponseEntity.ok(result);
            }
            throw new LoginException("Something went wrong",HttpStatus.BAD_REQUEST);


        } catch (AuthenticationException e) {
            throw new LoginException("Invaild Credential"+e.getMessage(),HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            throw new LoginException("Something went wrong"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login-guest")
    public ResponseEntity<?> getAccessToken(@RequestParam("guestId") String guestId){
        String accessTokenByGuestId= jwtUtil.generateGuestToken(guestId);
        String refreshTokenGuestId= jwtUtil.generateRefreshGuestToken();
        Map<String, String> result = new HashMap<>();
        result.put("token", accessTokenByGuestId);
        result.put("refreshToken",refreshTokenGuestId);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/login-check")
    public ResponseEntity<?> loginCheck(@RequestBody AuthRequest request) {
        Customer customer;
        if ("sms".equalsIgnoreCase(request.getTypeFormat())) {
            Long phoneno = Long.parseLong(request.getTypeValue());
            customer = customerRepository.findByPhonenoAndPassword(phoneno, request.getPassword())
                    .orElseThrow(() -> new RuntimeException("User not Found by using phoneno"));
        } else if ("email".equalsIgnoreCase(request.getTypeFormat())) {
            customer = customerRepository.findByUsernameAndPassword(request.getTypeValue(), request.getPassword())
                    .orElseThrow(() -> new RuntimeException("User not Found by using Username"));
        } else {
            throw new RuntimeException("Invalid login type format must be: sms or email");
        }

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setSuccess(true);
        messageResponseDto.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(messageResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (!jwtUtil.isTokenValid(refreshToken, user)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
            }
            String newAccessToken = jwtUtil.generateAccessToken(user);
            HashMap<String,String> hmap=new HashMap<>();
            return ResponseEntity.ok(hmap.put("access token",newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }

    @PostMapping("/refresh-guest")
    public ResponseEntity<?> refreshGuest(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtil.extractUsername(refreshToken);
//            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (!jwtUtil.validateGuestToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
            }
            String newAccessToken = jwtUtil.generateGuestToken(username);
            HashMap<String,String> hmap=new HashMap<>();
            return ResponseEntity.ok(hmap.put("access token",newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtUtil.blacklistToken(token);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/getallCustomer")
    public ResponseEntity<?> getAllCustomer(){
        try{
            return ResponseEntity.ok(customerRepository.findAll());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception "+ex.getMessage());
        }
    }
    @GetMapping("/login-testing")
    public Map<String,String> login() {
        Map<String, String> stringStringMap = new java.util.HashMap<>();
        stringStringMap.put("token", "hkjs");
        return stringStringMap;
    }
}
