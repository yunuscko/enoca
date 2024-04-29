package com.example.project.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class OrderDto {

    private Double totalPrice;

    private Set<EntryDto> entries;

    private Double  totalPriceOfProduct;

    private CustomerDto customerDto;

    public void setCode(String s) {
    }
}
