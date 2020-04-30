package com.example.energypricecalculatorapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ProductPricingResponse {
    private List<SingleProductPrice> productPrices = new LinkedList<>();
    private BigDecimal priceTotal;
}
