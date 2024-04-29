package com.example.project.dtos;

import lombok.Data;

@Data
public class ProductDto {

    private String code;

    private String name;

    private Integer stockValue;

    private Double price;
}
