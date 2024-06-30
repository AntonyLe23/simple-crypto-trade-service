package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class HuobiPrice implements Serializable {

  private String symbol;
  private BigDecimal bid;
  private BigDecimal ask;
}
