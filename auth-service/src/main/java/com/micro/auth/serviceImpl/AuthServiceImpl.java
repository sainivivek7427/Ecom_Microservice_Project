package com.micro.auth.serviceImpl;

import com.micro.auth.entity.Customer;
import com.micro.auth.repository.CustomerRepository;
import com.micro.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer registerCustomer(Customer customer){
        customer.setCuuid(UUID.randomUUID().toString());
        customer.setCreatedDate(System.currentTimeMillis());
        customer.setUpdateDate(System.currentTimeMillis());
        if(customer.getUsername().equals("ecomm_admin")){
            customer.setRole("Admin");
        }
        else{
            customer.setRole("User");
        }
        return customerRepository.save(customer);

    }

}
