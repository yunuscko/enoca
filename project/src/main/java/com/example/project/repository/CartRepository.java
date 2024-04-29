package com.example.project.repository;

import com.example.project.entity.Cart;
import com.example.project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findCartByCustomer(Customer customer);
}
