package com.micro.auth.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    private String cuuid;
    private String firstname;
    private String lastname;
    private String address;
    private Long phoneno;
    private String username;
    private String email;
    private String password;
    private String role;
    private String city;
    private String country;
    private Long pincode;
    private Long createdDate;
    private Long updateDate;


    public Customer() {
    }

    public Customer(String cuuid, String firstname, String lastname, String address, Long phoneno, String username,
                    String email, String password, String role, String city, String country, Long pincode, Long createdDate,
                    Long updateDate) {
        this.cuuid = cuuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phoneno = phoneno;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public String getCuuid() {
        return cuuid;
    }
    public void setCuuid(String cuuid) {
        this.cuuid = cuuid;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(Long phoneno) {
        this.phoneno = phoneno;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Long getPincode() {
        return pincode;
    }
    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Suggested code may be subject to a license. Learn more: ~LicenseLog:809410814.
// Suggested code may be subject to a license. Learn more: ~LicenseLog:3428689001.
    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
    // Suggested code may be subject to a license. Learn more: ~LicenseLog:1957615645.
    public Long getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Customer [cuuid=" + cuuid + ", firstname=" + firstname + ", lastname=" + lastname + ", address="
                + address + ", phoneno=" + phoneno + ", username=" + username + ", email=" + email + ", password="
                + password + ", role=" + role + ", city=" + city + ", country=" + country + ", pincode=" + pincode
                + ", createdDate=" + createdDate + ", updateDate=" + updateDate + "]";
    }




}
