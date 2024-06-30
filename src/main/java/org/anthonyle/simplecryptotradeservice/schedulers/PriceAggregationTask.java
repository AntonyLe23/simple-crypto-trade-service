package org.anthonyle.simplecryptotradeservice.schedulers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.anthonyle.simplecryptotradeservice.constants.BinanceCryptoPair;
import org.anthonyle.simplecryptotradeservice.constants.HuobiCryptoPair;
import org.anthonyle.simplecryptotradeservice.dto.BinancePrice;
import org.anthonyle.simplecryptotradeservice.dto.HuobiPrice;
import org.anthonyle.simplecryptotradeservice.dto.HuobiResponse;
import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class PriceAggregationTask {

  @Value("${crypto-pair.price.binance.source.url}")
  private String cryptoPairBinanceSourceUrl;

  @Value("${crypto-pair.price.houbi.source.url}")
  private String cryptoPairHoubiSourceUrl;

  private final RestTemplate restTemplate;
  private final AggregatedPriceService aggregatedPriceService;

  public PriceAggregationTask(final RestTemplateBuilder restTemplateBuilder, AggregatedPriceService aggregatedPriceService) {
    this.restTemplate = restTemplateBuilder.build();
    this.aggregatedPriceService = aggregatedPriceService;
  }

  @Scheduled(fixedRate = 10000)
  public void aggregatePrices() {
    log.info("Aggregating prices for ETHUSDT and BTCUSDT");

    BinancePrice[] binancePrices = fetchBinancePrices();
    HuobiResponse huobiResponse = fetchHuobiPrices();

    BigDecimal btcBidPrice = BigDecimal.ZERO;
    BigDecimal btcAskPrice = BigDecimal.valueOf(Double.MAX_VALUE);

    BigDecimal ethBidPrice = BigDecimal.ZERO;
    BigDecimal ethAskPrice = BigDecimal.valueOf(Double.MAX_VALUE);

    if (binancePrices != null) {
      btcBidPrice = getBinancePrice(binancePrices, BinanceCryptoPair.BTCUSDT, btcBidPrice, true);
      btcAskPrice = getBinancePrice(binancePrices, BinanceCryptoPair.BTCUSDT, btcAskPrice, false);

      ethBidPrice = getBinancePrice(binancePrices, BinanceCryptoPair.ETHUSDT, ethBidPrice, true);
      ethAskPrice = getBinancePrice(binancePrices, BinanceCryptoPair.ETHUSDT, ethAskPrice, false);
    }

    if (huobiResponse != null && huobiResponse.getData() != null) {
      btcBidPrice = getHuobiPrice(huobiResponse.getData(), HuobiCryptoPair.BTCUSDT, btcBidPrice, true);
      btcAskPrice = getHuobiPrice(huobiResponse.getData(), HuobiCryptoPair.BTCUSDT, btcAskPrice, false);

      ethBidPrice = getHuobiPrice(huobiResponse.getData(), HuobiCryptoPair.ETHUSDT, ethBidPrice, true);
      ethAskPrice = getHuobiPrice(huobiResponse.getData(), HuobiCryptoPair.ETHUSDT, ethAskPrice, false);
    }

    this.saveOrUpdateAggregatedPrices(btcBidPrice, btcAskPrice, CryptoPair.BTCUSDT);
    this.saveOrUpdateAggregatedPrices(ethBidPrice, ethAskPrice, CryptoPair.ETHUSDT);
  }

  private BinancePrice[] fetchBinancePrices() {
    return restTemplate.getForObject(cryptoPairBinanceSourceUrl, BinancePrice[].class);
  }

  private HuobiResponse fetchHuobiPrices() {
    return restTemplate.getForObject(cryptoPairHoubiSourceUrl, HuobiResponse.class);
  }

  private BigDecimal getBinancePrice(BinancePrice[] binancePrices, String symbol, BigDecimal existingPrice, boolean isBid) {
    return Arrays.stream(binancePrices)
        .filter(bp -> symbol.equals(bp.getSymbol()))
        .map(bp -> isBid ? bp.getBidPrice() : bp.getAskPrice())
        .map(price -> {
          if (Objects.isNull(existingPrice)) {
            return price;
          }
          return isBid ? price.max(existingPrice) : price.min(existingPrice);
        })
        .findFirst().orElse(existingPrice);
  }

  private BigDecimal getHuobiPrice(List<HuobiPrice> huobiPrices, String symbol, BigDecimal existingPrice, boolean isBid) {
    return huobiPrices.stream()
        .filter(hb -> symbol.equals(hb.getSymbol()))
        .map(hb -> isBid ? hb.getBid() : hb.getAsk())
        .map(price -> {
          if (Objects.isNull(existingPrice)) {
            return price;
          } else {
            return isBid ? price.max(existingPrice) : price.min(existingPrice);
          }
        })
        .findFirst().orElse(existingPrice);
  }

  private void saveOrUpdateAggregatedPrices(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair) {
    aggregatedPriceService.saveOrUpdateAggregatedPriceByCryptoPair(bidPrice, askPrice, cryptoPair);
  }
}
