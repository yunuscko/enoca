package com.example.project.service;

import com.example.project.dtos.ProductDto;
import com.example.project.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long ProductId);

    ProductDto createProduct(ProductDto productDto);

    void updateProduct(String productCode, ProductDto productDto)throws Exception;

    void deleteProduct(String productCode)throws Exception;

    Product getProductForCode(String code) throws Exception;

}
