package com.example.energypricecalculatorapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Getter
@Setter
public class QuarterlyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date quarterStart;
    private Date quarterEnd;

    @OneToMany
    @MapKey(name = "customerType")
    private Map<LoadShapePremium.CustomerType, LoadShapePremium> loadShapePremiums;

    @OneToMany
    @MapKey(name = "productType")
    private Map<ProductPrice.ProductType, ProductPrice> productPrices;
}
