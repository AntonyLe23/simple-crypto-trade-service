package org.anthonyle.simplecryptotradeservice.services;

import org.anthonyle.simplecryptotradeservice.dto.TradeRequest;
import org.anthonyle.simplecryptotradeservice.dto.TradeResult;

public interface TradeService {

  TradeResult executeTrade(TradeRequest tradeRequest);
}
