package org.anthonyle.simplecryptotradeservice.repositories;

import java.util.Optional;

import org.anthonyle.simplecryptotradeservice.models.CryptoWallet;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoWalletRepo extends JpaRepository<CryptoWallet, Long> {
  Optional<CryptoWallet> findByIdAndUser(Long id, User user);
}
