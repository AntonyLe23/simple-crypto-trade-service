package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;

import lombok.Data;

@Data
public class BestPriceExchange implements Serializable {

  private CryptoPair cryptoPair;
  private BigDecimal bidPrice;
  private BigDecimal askPrice;
}
