package com.example.project.service;

import com.example.project.dtos.CustomerDto;
import com.example.project.entity.Customer;

public interface CustomerService {

    void addCustomer(CustomerDto customerDto)throws Exception;
    Customer findCustomerByUsername(String username) throws Exception;


}
