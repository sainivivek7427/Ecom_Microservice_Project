package com.micro.auth.serviceImpl;

import com.micro.auth.entity.Customer;
import com.micro.auth.repository.CustomerRepository;
import com.micro.auth.service.LoginRegService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class LogRegServiceimpl implements LoginRegService {
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
