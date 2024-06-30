package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.enums.TradeType;

import lombok.Data;

@Data
public class TradeRequest implements Serializable {

  private Long userId;
  private Long walletId;
  private TradeType tradeType; // "BUY" or "SELL"
  private CryptoPair cryptoPair; // e.g., "ETHUSDT", "BTCUSDT"
  private BigDecimal unit;
}
