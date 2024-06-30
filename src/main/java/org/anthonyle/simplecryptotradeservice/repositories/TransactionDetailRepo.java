package org.anthonyle.simplecryptotradeservice.repositories;

import org.anthonyle.simplecryptotradeservice.models.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Long> {

}
