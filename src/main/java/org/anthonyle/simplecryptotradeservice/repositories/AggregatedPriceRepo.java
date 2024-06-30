package org.anthonyle.simplecryptotradeservice.repositories;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatedPriceRepo extends JpaRepository<AggregatedPrice, Long> {
  AggregatedPrice findByCryptoPair(CryptoPair cryptoPair);
}
