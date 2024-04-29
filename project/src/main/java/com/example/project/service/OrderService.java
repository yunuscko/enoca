package com.example.project.service;

import com.example.project.dtos.OrderDto;
import com.example.project.entity.Order;

import java.util.List;

public interface OrderService {

    public void placeOrder();

    Order getOrderForCode(String code);

    public List<OrderDto> getAllOrdersForCustomer();
}
