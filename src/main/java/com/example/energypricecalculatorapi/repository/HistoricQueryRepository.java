package com.example.energypricecalculatorapi.repository;

import com.example.energypricecalculatorapi.model.HistoricQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricQueryRepository extends JpaRepository<HistoricQuery, Long> {
    List<HistoricQuery> findTop10ByOrderByCreatedAtDesc();
}
