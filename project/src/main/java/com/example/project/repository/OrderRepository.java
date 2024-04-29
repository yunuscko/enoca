package com.example.project.repository;

import com.example.project.entity.Customer;
import com.example.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getAllOrderByCustomer(Customer customer);
}
