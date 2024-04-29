package com.example.project.serviceImpl;

import com.example.project.Exception.ProductIsNotFound;
import com.example.project.dtos.EntryDto;
import com.example.project.dtos.OrderDto;
import com.example.project.entity.Cart;
import com.example.project.entity.Customer;
import com.example.project.entity.Entry;
import com.example.project.entity.Order;
import com.example.project.repository.OrderRepository;
import com.example.project.service.OrderService;
import com.example.project.service.SessionService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private SessionService sessionService;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public void placeOrder() {
        Order order=new Order();
        Customer customer=sessionService.getCurrentCustomer();
        Cart cart=sessionService.getCurrentCart();

        order.setEntries(cart.getEntries());
        order.setEntries(cart.getEntries());
        order.setTotalPrice(cart.getTotalPriceOfProduct());
        order.setTotalPrice(order.getTotalPrice());
        orderRepository.save(order);

    }

    @Override
    public Order getOrderForCode(String code) {

        Optional<Order> orderOptional=orderRepository.findById(Long.valueOf(code));
        if(orderOptional.isPresent()){
            return orderOptional.get();
        }else{
            throw new ProductIsNotFound("order is not found");
        }
    }

    @Override
    public List<OrderDto> getAllOrdersForCustomer() {
        Customer customer=sessionService.getCurrentCustomer();
        List<Order> orders=orderRepository.getAllOrderByCustomer(customer);
        List<OrderDto> orderDtos=new LinkedList<>();
        if(CollectionUtils.isEmpty(orders)){
            return Collections.emptyList();
        }else{
            for(Order order:orders){
                OrderDto orderDto=new OrderDto();
                Set<EntryDto> entries=order.getEntries()
                        .stream()
                        .map(entryDto -> modelMapper.map(entryDto , EntryDto.class))
                        .collect(Collectors.toSet());
                orderDto.setEntries(entries);
                orderDto.setCode(String.valueOf(order.getId()));
                orderDto.setTotalPrice(order.getTotalPrice());
                orderDto.setTotalPriceOfProduct(order.getTotalPriceOfProduct());
                orderDtos.add(orderDto);
            }
            return orderDtos;
        }

    }
}
