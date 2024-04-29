package com.example.project.Exception;

public class ProductIsNotFound extends RuntimeException{
    public ProductIsNotFound(String message){
        super(message);
    }
}
