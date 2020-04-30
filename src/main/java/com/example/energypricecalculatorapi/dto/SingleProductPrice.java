package com.example.energypricecalculatorapi.dto;

import com.example.energypricecalculatorapi.model.ProductPrice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SingleProductPrice {
    private ProductPrice.ProductType type;
    private BigDecimal price;
}
