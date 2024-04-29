package com.example.project.controller;

import com.example.project.entity.Customer;
import com.example.project.service.CartService;
import com.example.project.service.SessionService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private SessionService sessionService;

    @GetMapping("/getCart")
    public ResponseEntity getCartForCustomer(){
        Customer customer=sessionService.getCurrentCustomer();
        try {
                return new ResponseEntity<>(cartService.getCartForCustomer(customer), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCart")
    public ResponseEntity updateCart(@RequestParam String productCode,@RequestParam int quantity){
     try {
         cartService.updateCart(productCode,quantity);
         return new ResponseEntity<>("Cart succesfully updated",HttpStatus.OK);
     }catch (Exception e){
         return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
     }

    }
    @DeleteMapping("/emptyCart")
    public ResponseEntity emptyCart(){
        try {
            cartService.emptyCart();
            return new ResponseEntity<>("Cart is empty now.",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
