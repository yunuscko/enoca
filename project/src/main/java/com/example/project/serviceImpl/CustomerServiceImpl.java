package com.example.project.serviceImpl;

import com.example.project.Exception.ProductIsNotFound;
import com.example.project.dtos.CustomerDto;
import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import com.example.project.service.CustomerService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public void addCustomer(CustomerDto customerDto) throws Exception {
        Optional<Customer> optionalCustomer = customerRepository.getCustomerByUsername(customerDto.getUsername());
        if(optionalCustomer.isPresent()){
            throw new Exception("Customer is not found");
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
    }
    @Override
    public Customer findCustomerByUsername(String username) throws Exception {
        final Optional<Customer> optionalCustomer = customerRepository.getCustomerByUsername(username);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }
        throw new Exception("Customer is not found with username: " + username);
    }


    }


