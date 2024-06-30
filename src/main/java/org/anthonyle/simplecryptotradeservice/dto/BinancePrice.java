package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BinancePrice implements Serializable {

  private String symbol;
  private BigDecimal bidPrice;
  private BigDecimal askPrice;
}
