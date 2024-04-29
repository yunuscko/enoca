package com.example.project.dtos;

import com.example.project.entity.Entry;
import lombok.Data;

import java.util.Set;

@Data
public class CartDto {

    private Set<EntryDto> entries;

    private String totalPriceOfProduct;

    private CustomerDto customerDto;
}
