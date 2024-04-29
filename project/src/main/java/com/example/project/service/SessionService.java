package com.example.project.service;

import com.example.project.entity.Cart;
import com.example.project.entity.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope // her kullanıcı için yeni bir oturum açar ve bu örnek sadece o kullanıcının oturumu süresince yaşar.
public class SessionService {

    private Customer currentCustomer;

    private Cart currentCart;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void setCurrentCart(Cart currentCart) {
        this.currentCart = currentCart;
    }
}
