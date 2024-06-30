package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CryptoWalletResponse implements Serializable {

  private BigDecimal usdtBalance;
  private BigDecimal btcUnit;
  private BigDecimal ethUnit;
  private LocalDateTime createdTime;
}
