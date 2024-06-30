package org.anthonyle.simplecryptotradeservice.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String username;

  @OneToMany(mappedBy = "user")
  private List<CryptoWallet> cryptoWallets = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<TransactionDetail> transactionDetails = new ArrayList<>();
}
