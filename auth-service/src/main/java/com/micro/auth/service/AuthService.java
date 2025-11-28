package com.micro.auth.service;

import com.micro.auth.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public Customer registerCustomer(Customer customer);
}
