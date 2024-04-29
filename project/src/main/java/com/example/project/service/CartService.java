package com.example.project.service;

import com.example.project.Exception.WrongParameter;
import com.example.project.dtos.CartDto;
import com.example.project.entity.Cart;
import com.example.project.entity.Customer;

public interface CartService {

    Cart getCartForCustomer(Customer customer);

    void updateCart(String productCode,int quantity) throws Exception;

    void emptyCart();

    void calculateCart(Cart cart);

    void addProductToCart(String ProductCode,int quantity) throws Exception;

    void removeProductToCart(String ProductCode);
}
