package org.anthonyle.simplecryptotradeservice.services;

import java.math.BigDecimal;
import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.BestPriceExchange;
import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;

public interface AggregatedPriceService {

  void createNewAggregatedPrice(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair);

  List<BestPriceExchange> getAllLatestBestAggregatedPricesByCryptoPairs();

  AggregatedPrice getLatestBestAggregatedPricesByCryptoPairs(CryptoPair cryptoPair);
}
