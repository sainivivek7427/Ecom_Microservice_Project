package com.micro.auth.controller;

import com.micro.auth.config.JwtUtil;
import com.micro.auth.config.UserDetailServiceimpl;
import com.micro.auth.entity.Customer;
import com.micro.auth.model.AuthRequest;
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

    @PostMapping("/reg")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        Customer customerResponse=authService.registerCustomer(customer);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            System.out.println("jkala");
            Customer customer=null;
            if(request.getTypeFormat().equals("sms")){
                Long phoneno=Long.parseLong(request.getTypeValue());
                customer=customerRepository.findByPhonenoAndPassword(phoneno, request.getPassword()).orElseThrow(()->new NullPointerException("User not Found by using phoneno"));
            }
            else if(request.getTypeFormat().equals("email")){
                customer=customerRepository.findByUsername(request.getTypeValue()).orElseThrow(()->new NullPointerException("User not Found by using Username"));
            }
            System.out.println("Username "+customer.getUsername()+" Password "+request.getPassword());
            //authentaication manager call the userdetaiservice class to load the database and match the detail if correct or not
            Authentication auth =  authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customer.getUsername(), request.getPassword())
            );
            System.out.println("Authentication "+auth.getDetails());
            UserDetails user = (UserDetails) auth.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);
            Map<String,String> result=new HashMap<>();
            result.put("token", accessToken);
            result.put("refreshToken", refreshToken);
            return ResponseEntity.ok(result);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials "+e.getMessage() );
        }
    }

    @PostMapping("/login-check")
    public ResponseEntity<?> loginCheck(@RequestBody AuthRequest request) {
        try {
            System.out.println("jkala");
            Customer customer=null;
            if(request.getTypeFormat().equals("sms")){
                Long phoneno=Long.parseLong(request.getTypeValue());
                customer=customerRepository.findByPhonenoAndPassword(phoneno, request.getPassword()).orElseThrow(()->new NullPointerException("User not Found by using phoneno"));
            }
            else if(request.getTypeFormat().equals("email")){
                customer=customerRepository.findByUsernameAndPassword(request.getTypeValue(), request.getPassword()).orElseThrow(()->new NullPointerException("User not Found by using Username"));
            }
            System.out.println("Username "+customer.getUsername()+" Password "+request.getPassword());
            MessageResponseDto messageResponseDto = new MessageResponseDto();
            messageResponseDto.setSuccess(true);
            messageResponseDto.setStatus(HttpStatus.OK);
            System.out.println("Correct fields");
            return ResponseEntity.ok(messageResponseDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials "+e.getMessage() );
        }
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
