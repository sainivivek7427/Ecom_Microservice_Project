package com.micro.auth.config;

import com.micro.auth.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {
    private final Customer customer;
    public UserDetail(Customer customer){
        this.customer=customer;
    }
    @Override
    public String getUsername() {
        return customer.getUsername();
    }
    @Override
    public String getPassword() {
        return customer.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(customer.getRole()));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    //  //add gmail verify
    // public boolean isEnabledCustomer() {
    //     return customer.isEnabled();
    // }


}

