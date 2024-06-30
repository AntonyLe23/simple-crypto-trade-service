package org.anthonyle.simplecryptotradeservice.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aggregated_prices")
@Getter
@Setter
public class AggregatedPrice extends BaseEntity {

  @Column(name = "crypto_pair", nullable = false)
  @Enumerated(EnumType.STRING)
  private CryptoPair cryptoPair;

  @Column(name = "bid_price")
  private BigDecimal bidPrice;

  @Column(name = "ask_price")
  private BigDecimal askPrice;

  @Column(name = "created_time")
  private LocalDateTime createdDateTime;
}
