package org.anthonyle.simplecryptotradeservice.controllers;

import org.anthonyle.simplecryptotradeservice.dto.ApiResponse;
import org.anthonyle.simplecryptotradeservice.dto.TradeRequest;
import org.anthonyle.simplecryptotradeservice.dto.TradeResult;
import org.anthonyle.simplecryptotradeservice.exceptions.InsufficientBalanceException;
import org.anthonyle.simplecryptotradeservice.exceptions.TradeException;
import org.anthonyle.simplecryptotradeservice.services.TradeService;
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
}
