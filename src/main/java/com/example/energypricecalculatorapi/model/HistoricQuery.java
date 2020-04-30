package com.example.energypricecalculatorapi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class HistoricQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date createdAt;

    private Date periodStart;
    private Date periodEnd;

    @Enumerated(EnumType.STRING)
    private LoadShapePremium.CustomerType customerType;

    @ElementCollection
    @CollectionTable(name = "historic_query_product_types", joinColumns = @JoinColumn(name = "query_id"))
    @Enumerated(EnumType.STRING)
    private List<ProductPrice.ProductType> productTypes;
}
