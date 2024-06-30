package org.anthonyle.simplecryptotradeservice.services;

import java.util.List;

import org.anthonyle.simplecryptotradeservice.dto.CryptoWalletResponse;

public interface CryptoWalletService {

  List<CryptoWalletResponse> getWalletsByUserId(Long userId);

}
