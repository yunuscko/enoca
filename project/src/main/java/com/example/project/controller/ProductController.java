package com.example.project.controller;

import com.example.project.dtos.ProductDto;
import com.example.project.entity.Product;
import com.example.project.service.CartService;
import com.example.project.service.ProductService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private CartService cartService;

    @GetMapping("/getAllProduct")
    public ResponseEntity getAllProduct(){
        try {
            return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // ali erene sor
    @GetMapping("/getProduct/{productCode}")
    public ResponseEntity getProductByCode(@PathVariable String productCode){
        try{
            Product product=productService.getProductForCode(productCode);
            return new ResponseEntity<>(modelMapper.map(product, ProductDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        }

        @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody ProductDto productDto){
        try {
            productService.createProduct(productDto);
            return new ResponseEntity<>("product succesfully added",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateProduct/{productCode}")
    public ResponseEntity updateProduct(@RequestBody ProductDto productDto,@PathVariable String productCode)  {
        try{
            productService.updateProduct(productCode,productDto);
            return new ResponseEntity<>("product updated",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteProduct/{productCode}")
    public ResponseEntity deleteProduct(@PathVariable String productCode){
        try {
            productService.deleteProduct(productCode);
            return new ResponseEntity<>("product deleted",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-Product-To-Cart/{productCode}/{qty}")
    public ResponseEntity addProductToCart(@PathVariable String productCode,@PathVariable Integer qty){
        try {
            cartService.addProductToCart(productCode,qty);
            return new ResponseEntity<>("added product to cart",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("remove-Product-To-Cart/{productCode}")
    public ResponseEntity removeProductToCart(@PathVariable String productCode){
     try {
         cartService.removeProductToCart(productCode);
         return new ResponseEntity<>("deleted product from cart",HttpStatus.OK);
     }catch (Exception e){
         return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
     }

    }
}
