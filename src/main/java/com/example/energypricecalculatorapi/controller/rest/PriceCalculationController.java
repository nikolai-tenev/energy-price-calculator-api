package com.example.energypricecalculatorapi.controller.rest;

import com.example.energypricecalculatorapi.dto.ProductPricingResponse;
import com.example.energypricecalculatorapi.dto.SingleProductPrice;
import com.example.energypricecalculatorapi.model.HistoricQuery;
import com.example.energypricecalculatorapi.model.ProductPrice;
import com.example.energypricecalculatorapi.model.QuarterlyPrice;
import com.example.energypricecalculatorapi.repository.HistoricQueryRepository;
import com.example.energypricecalculatorapi.repository.QuarterlyPriceRepository;
import com.example.energypricecalculatorapi.specification.QuarterlyPriceSpecifications;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest controller for calculating energy product prices.
 */
@RestController
@RequestMapping("/api/price-calculations")
@CrossOrigin(
        origins = {"http://localhost", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
public class PriceCalculationController {

    private final QuarterlyPriceRepository quarterlyPriceRepository;
    private final HistoricQueryRepository historicQueryRepository;

    public PriceCalculationController(QuarterlyPriceRepository quarterlyPriceRepository, HistoricQueryRepository historicQueryRepository) {
        this.quarterlyPriceRepository = quarterlyPriceRepository;
        this.historicQueryRepository = historicQueryRepository;
    }

    /**
     * Calculates average product prices for a given period of time and a sum of the product prices.
     *
     * @param query all the query vars sent from client.
     * @return calculated product prices.
     */
    @PostMapping
    public ProductPricingResponse calculatePrice(@RequestBody @Valid HistoricQuery query) {
        historicQueryRepository.save(query);

        if (query.getPeriodStart().after(query.getPeriodEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Period start can't be after period end.");
        }

        List<QuarterlyPrice> allQuarters = quarterlyPriceRepository.findAll(QuarterlyPriceSpecifications.periodInQuarterSpec(query.getPeriodStart(), query.getPeriodEnd()));
        Map<ProductPrice.ProductType, BigDecimal> quarterlyPriceTotals = new HashMap<>();

        ProductPricingResponse response = new ProductPricingResponse();
        response.setPriceTotal(BigDecimal.ZERO);

        for (QuarterlyPrice price : allQuarters) {
            BigDecimal loadShapePremium = price.getLoadShapePremiums().get(query.getCustomerType()).getPrice();

            for (ProductPrice.ProductType productType : query.getProductTypes()) {
                BigDecimal currentTotal = quarterlyPriceTotals.getOrDefault(productType, BigDecimal.ZERO);
                BigDecimal priceToAdd = loadShapePremium.multiply(price.getProductPrices().get(productType).getPrice());

                quarterlyPriceTotals.put(productType, currentTotal.add(priceToAdd));
            }
        }

        for (Map.Entry<ProductPrice.ProductType, BigDecimal> priceTotal : quarterlyPriceTotals.entrySet()) {
            SingleProductPrice singleProductPrice = new SingleProductPrice();

            singleProductPrice.setType(priceTotal.getKey());
            singleProductPrice.setPrice(priceTotal.getValue().divide(BigDecimal.valueOf(allQuarters.size()), RoundingMode.HALF_UP));

            BigDecimal newPriceTotal = response.getPriceTotal().add(singleProductPrice.getPrice());

            response.getProductPrices().add(singleProductPrice);
            response.setPriceTotal(newPriceTotal);
        }

        return response;
    }

    @GetMapping
    public List<HistoricQuery> getQueryLog() {
        return historicQueryRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
