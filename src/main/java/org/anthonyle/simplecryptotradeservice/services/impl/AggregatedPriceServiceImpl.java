package org.anthonyle.simplecryptotradeservice.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.anthonyle.simplecryptotradeservice.dto.BestPriceExchange;
import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;
import org.anthonyle.simplecryptotradeservice.repositories.AggregatedPriceRepo;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AggregatedPriceServiceImpl implements AggregatedPriceService {

  private final AggregatedPriceRepo aggregatedPriceRepo;

  @Override
  @Transactional
  public void createNewAggregatedPrice(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair) {
    AggregatedPrice newAggregatedPrice = new AggregatedPrice();
    newAggregatedPrice.setBidPrice(bidPrice);
    newAggregatedPrice.setAskPrice(askPrice);
    newAggregatedPrice.setCreatedDateTime(LocalDateTime.now());
    newAggregatedPrice.setCryptoPair(cryptoPair);
    aggregatedPriceRepo.save(newAggregatedPrice);
  }

  @Override
  public List<BestPriceExchange> getLatestBestAggregatedPricesByCryptoPairs() {
    return aggregatedPriceRepo.findAllLatestAggregatedPriceByCryptoPairs().stream()
        .map(aggregatedPrice -> {
          BestPriceExchange bestPriceExchange = new BestPriceExchange();
          bestPriceExchange.setBidPrice(aggregatedPrice.getBidPrice());
          bestPriceExchange.setAskPrice(aggregatedPrice.getAskPrice());
          bestPriceExchange.setCryptoPair(aggregatedPrice.getCryptoPair().name());
          return bestPriceExchange;
        })
        .collect(Collectors.toList());
  }
}
