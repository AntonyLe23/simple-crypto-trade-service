package org.anthonyle.simplecryptotradeservice.services;

import java.math.BigDecimal;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;

public interface AggregatedPriceService {

  void saveOrUpdateAggregatedPriceByCryptoPair(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair);
}
