package org.anthonyle.simplecryptotradeservice.services;

import org.anthonyle.simplecryptotradeservice.dto.BinancePrice;
import org.anthonyle.simplecryptotradeservice.dto.HuobiResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestRestService {

  private final RestTemplate restTemplate;

  public TestRestService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public void test () {
    var binancePrice = restTemplate.getForObject("https://api.binance.com/api/v3/ticker/bookTicker", BinancePrice[].class);
    var huobiPrice = restTemplate.getForObject("https://api.huobi.pro/market/tickers", HuobiResponse.class);

    System.out.println(binancePrice);
    System.out.println(huobiPrice);

  }
}
