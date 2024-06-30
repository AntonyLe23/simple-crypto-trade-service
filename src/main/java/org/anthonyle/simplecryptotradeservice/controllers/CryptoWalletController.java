package org.anthonyle.simplecryptotradeservice.controllers;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.ApiResponse;
import org.anthonyle.simplecryptotradeservice.dto.CryptoWalletResponse;
import org.anthonyle.simplecryptotradeservice.services.CryptoWalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/crypto-wallet")
@RequiredArgsConstructor
public class CryptoWalletController {

  private final CryptoWalletService cryptoWalletService;

  @GetMapping("/user/{id}")
  public ApiResponse<List<CryptoWalletResponse>> getUser(@PathVariable("id") Long userId) {
    ApiResponse<List<CryptoWalletResponse>> apiResponse = new ApiResponse<>();
    apiResponse.setData(cryptoWalletService.getWalletsByUserId(userId));
    return apiResponse;
  }
}
