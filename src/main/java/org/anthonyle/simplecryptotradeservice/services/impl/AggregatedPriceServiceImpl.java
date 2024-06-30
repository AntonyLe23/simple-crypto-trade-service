package org.anthonyle.simplecryptotradeservice.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;
import org.anthonyle.simplecryptotradeservice.repositories.AggregatedPriceRepo;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AggregatedPriceServiceImpl implements AggregatedPriceService {

  private final AggregatedPriceRepo aggregatedPriceRepo;

  @Override
  @Transactional
  public void saveOrUpdateAggregatedPriceByCryptoPair(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair) {
    AggregatedPrice aggregatedPrice = aggregatedPriceRepo.findByCryptoPair(cryptoPair);

    if (Objects.isNull(aggregatedPrice)) {
      aggregatedPrice = new AggregatedPrice();
      aggregatedPrice.setCryptoPair(cryptoPair);
    }

    aggregatedPrice.setBidPrice(bidPrice);
    aggregatedPrice.setAskPrice(askPrice);
    aggregatedPrice.setUpdatedTime(LocalDateTime.now());
    aggregatedPriceRepo.save(aggregatedPrice);
  }
}
