package org.anthonyle.simplecryptotradeservice.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crypto_wallets")
@Getter
@Setter
public class CryptoWallet extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "usdt_balance")
  private BigDecimal usdtBalance = new BigDecimal(50000);

  @Column(name = "btc_unit")
  private BigDecimal btcUnit = BigDecimal.ZERO;

  @Column(name = "eth_unit")
  private BigDecimal ethUnit = BigDecimal.ZERO;

  @Column(name = "created_time")
  private LocalDateTime createdTime = LocalDateTime.now();
}
