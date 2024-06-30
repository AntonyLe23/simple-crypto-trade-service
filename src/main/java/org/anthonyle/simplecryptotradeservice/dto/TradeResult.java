package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.enums.TradeType;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TradeResult implements Serializable {

  private Long cryptoWalletId;
  private TradeType tradeType;
  private CryptoPair cryptoPair;
  private BigDecimal pricePerUnit;
  private BigDecimal unit;
  private BigDecimal totalExecutedAmount;
  private LocalDateTime createdTime;
}
