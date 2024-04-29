package com.example.project.dtos;

import com.example.project.entity.Product;
import lombok.Data;

@Data
public class EntryDto {

    private Product product;

    private Integer quantity;

}
