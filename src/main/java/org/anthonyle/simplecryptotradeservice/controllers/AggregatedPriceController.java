package org.anthonyle.simplecryptotradeservice.controllers;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.ApiResponse;
import org.anthonyle.simplecryptotradeservice.dto.BestPriceExchange;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/aggregated-price")
@RequiredArgsConstructor
public class AggregatedPriceController {
  private final AggregatedPriceService aggregatedPriceService;

  @GetMapping("/crypto-pairs/best-prices")
  public ApiResponse<List<BestPriceExchange>> getBestPrice() {
    ApiResponse<List<BestPriceExchange>> apiResponse = new ApiResponse<>();
    apiResponse.setData(aggregatedPriceService.getLatestBestAggregatedPricesByCryptoPairs());
    return apiResponse;
  }
}
