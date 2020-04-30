package com.example.energypricecalculatorapi.repository;

import com.example.energypricecalculatorapi.model.QuarterlyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuarterlyPriceRepository extends JpaRepository<QuarterlyPrice, Long>, JpaSpecificationExecutor<QuarterlyPrice> {
}
