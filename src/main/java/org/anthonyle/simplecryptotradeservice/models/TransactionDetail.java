package org.anthonyle.simplecryptotradeservice.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.enums.TradeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_details")
@Getter
@Setter
public class TransactionDetail extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "crypto_wallet_id", nullable = false)
  private CryptoWallet cryptoWallet;

  @Column(name = "trade_type")
  @Enumerated(EnumType.STRING)
  private TradeType tradeType;

  @Column(name = "crypto_pair", nullable = false)
  @Enumerated(EnumType.STRING)
  private CryptoPair cryptoPair;

  @Column(name = "price_per_unit")
  private BigDecimal pricePerUnit;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "total_transaction_amount")
  private BigDecimal totalTransactionAmount;

  @Column(name = "created_time")
  private LocalDateTime createdDateTime = LocalDateTime.now();
}
