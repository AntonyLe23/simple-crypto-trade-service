package org.anthonyle.simplecryptotradeservice.services;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.TradeRequest;
import org.anthonyle.simplecryptotradeservice.dto.TradeResult;

public interface TradeService {

  TradeResult executeTrade(TradeRequest tradeRequest);

  List<TradeResult> getTradesByUser(Long userId);
}
