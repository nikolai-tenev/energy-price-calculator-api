package com.example.energypricecalculatorapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class LoadShapePremium {

    public enum CustomerType {
        MINING, INDUSTRIAL, COMMERCIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    private BigDecimal price;
}
