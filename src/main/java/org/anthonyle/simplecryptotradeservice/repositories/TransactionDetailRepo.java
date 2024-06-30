package org.anthonyle.simplecryptotradeservice.repositories;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.models.TransactionDetail;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Long> {

  List<TransactionDetail> findByUser(User user);
}
