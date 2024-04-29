package com.example.project.serviceImpl;

import com.example.project.Exception.ProductIsNotFound;
import com.example.project.dtos.ProductDto;
import com.example.project.entity.Product;
import com.example.project.repository.ProductRepository;
import com.example.project.service.ProductService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductRepository productRepository;

    @Resource
    ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products=productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ProductDto getProductById(Long ProductId) {
        Optional<Product> optionalProduct=productRepository.findById(ProductId);
        if(optionalProduct.isPresent()){
            Product product=optionalProduct.get();
            return modelMapper.map(product,ProductDto.class);
        }else{
            throw new ProductIsNotFound("product is not found ->"+ProductId);
        }
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (productDto == null)
            throw new IllegalArgumentException("product can not be null");

        Product product = modelMapper.map(productDto, Product.class);
        return modelMapper.map(productRepository.save(product),ProductDto .class);
}


    @Override
    public void updateProduct(String code, ProductDto productDto) throws Exception {
        Product product = this.getProductForCode(code);
        if(productDto.getCode() != null){
            product.setCode(productDto.getCode());
        }
        if(productDto.getName() != null){
            product.setName(productDto.getName());
        }
        if(productDto.getStockValue() != null){
            product.setStockValue(productDto.getStockValue());
        }
        if(productDto.getPrice() != null){
            product.setPrice(productDto.getPrice());
        }
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(String code) throws Exception {
        final Product product = this.getProductForCode(code);
        productRepository.delete(product);
    }

    @Override
    public Product getProductForCode(String code) throws Exception {
        final Optional<Product> optionalProduct = productRepository.getProductByCode(code);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new Exception("Product not found for code: " + code);
        }
    }
}
