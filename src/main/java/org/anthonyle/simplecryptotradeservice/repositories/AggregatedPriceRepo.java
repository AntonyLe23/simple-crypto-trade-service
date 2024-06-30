package org.anthonyle.simplecryptotradeservice.repositories;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.enums.CryptoPair;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AggregatedPriceRepo extends JpaRepository<AggregatedPrice, Long> {

  @Query("SELECT ap FROM AggregatedPrice ap "
      + "WHERE ap.createdDateTime = (SELECT MAX(ap2.createdDateTime) FROM AggregatedPrice ap2 WHERE ap2.cryptoPair = ap.cryptoPair)")
  List<AggregatedPrice> findAllLatestAggregatedPriceByCryptoPairs();

  @Query("SELECT ap FROM AggregatedPrice ap "
      + "WHERE ap.createdDateTime = (SELECT MAX(ap2.createdDateTime) FROM AggregatedPrice ap2 WHERE ap2.cryptoPair = ap.cryptoPair) "
      + "AND ap.cryptoPair = :cryptoPair")
  AggregatedPrice findLatestAggregatedPriceByCryptoPairs(@Param("cryptoPair") CryptoPair cryptoPair);
}
