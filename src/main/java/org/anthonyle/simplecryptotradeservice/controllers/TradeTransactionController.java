package org.anthonyle.simplecryptotradeservice.controllers;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.ApiResponse;
import org.anthonyle.simplecryptotradeservice.dto.TradeRequest;
import org.anthonyle.simplecryptotradeservice.dto.TradeResult;
import org.anthonyle.simplecryptotradeservice.services.TradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradeTransactionController {

  private final TradeService tradeService;

  @PostMapping("/execute")
  public ApiResponse<TradeResult> executeTrade(@RequestBody TradeRequest tradeRequest) {
    ApiResponse<TradeResult> apiResponse = new ApiResponse<>();
    TradeResult tradeResult = tradeService.executeTrade(tradeRequest);
    apiResponse.setData(tradeResult);
    apiResponse.setMessage("Trade executed successfully");
    return apiResponse;
  }

  @GetMapping("/history/user/{id}")
  public ApiResponse<List<TradeResult>> getTradeHistoryByUser(@PathVariable("id") Long userId) {
    ApiResponse<List<TradeResult>> apiResponse = new ApiResponse<>();
    apiResponse.setData(tradeService.getTradesByUser(userId));
    return apiResponse;
  }
}
