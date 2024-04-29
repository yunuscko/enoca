package com.example.project.controller;

import com.example.project.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity placeOrder(){
        try{
            orderService.placeOrder();
            return new ResponseEntity<>("succesfully placing order ", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrder/{orderCode}")
    public ResponseEntity getOrderByCode(@PathVariable String orderCode){
        try{
            orderService.getOrderForCode(orderCode);
            return new ResponseEntity<>("getting order"+orderCode,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllOrderForCustomer")
    public ResponseEntity getAllOrderForCustomer(){
     try {

             orderService.getAllOrdersForCustomer();
             return new ResponseEntity<>("getting all order for customer",HttpStatus.OK);

     }catch (Exception e){
         return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
     }

    }

}
