package com.example.energypricecalculatorapi.specification;

import com.example.energypricecalculatorapi.model.QuarterlyPrice;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.Date;

public class QuarterlyPriceSpecifications {
    /**
     * JPA Specification used to filter quarters by given period start and period end.
     *
     * @param periodStart period start
     * @param periodEnd   period end
     * @return JPA specification
     */
    public static Specification<QuarterlyPrice> periodInQuarterSpec(Date periodStart, Date periodEnd) {
        return (Specification<QuarterlyPrice>) (root, query, cb) -> {

            final Path<Date> quarterStart = root.get("quarterStart");
            final Path<Date> quarterEnd = root.get("quarterEnd");

            return cb.or(
                    cb.and(cb.greaterThanOrEqualTo(quarterStart, periodStart), cb.lessThanOrEqualTo(quarterEnd, periodEnd)),
                    cb.and(cb.lessThanOrEqualTo(quarterStart, periodStart), cb.greaterThanOrEqualTo(quarterEnd, periodEnd)),

                    cb.and(cb.greaterThanOrEqualTo(quarterStart, periodStart), cb.lessThanOrEqualTo(quarterStart, periodEnd)),
                    cb.and(cb.greaterThanOrEqualTo(quarterEnd, periodStart), cb.lessThanOrEqualTo(quarterEnd, periodEnd))
            );
        };
    }
}
