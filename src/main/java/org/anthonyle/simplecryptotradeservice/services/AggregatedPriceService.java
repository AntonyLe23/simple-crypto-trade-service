package org.anthonyle.simplecryptotradeservice.services;

import java.math.BigDecimal;
import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.BestPriceExchange;
import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;

public interface AggregatedPriceService {

  void createNewAggregatedPrice(BigDecimal bidPrice, BigDecimal askPrice, CryptoPair cryptoPair);

  List<BestPriceExchange> getLatestBestAggregatedPricesByCryptoPairs();
}
