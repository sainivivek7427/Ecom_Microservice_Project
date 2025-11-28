package com.micro.auth.config;

import com.micro.auth.entity.Customer;
import com.micro.auth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceimpl implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load data ");
        Customer customer=customerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found "+username));
        System.out.println("update "+customer);
        return new UserDetail(customer);
    }


}

