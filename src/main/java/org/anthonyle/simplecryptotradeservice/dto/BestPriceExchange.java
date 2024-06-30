package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BestPriceExchange implements Serializable {

  private String cryptoPair;
  private BigDecimal bidPrice;
  private BigDecimal askPrice;
}
