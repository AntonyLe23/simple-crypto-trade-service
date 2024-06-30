package org.anthonyle.simplecryptotradeservice.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.anthonyle.simplecryptotradeservice.dto.CryptoWalletResponse;
import org.anthonyle.simplecryptotradeservice.exceptions.BaseException;
import org.anthonyle.simplecryptotradeservice.exceptions.ErrorCode;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.anthonyle.simplecryptotradeservice.repositories.CryptoWalletRepo;
import org.anthonyle.simplecryptotradeservice.repositories.UserRepo;
import org.anthonyle.simplecryptotradeservice.services.CryptoWalletService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoWalletServiceImpl implements CryptoWalletService {

  private final CryptoWalletRepo cryptoWalletRepo;
  private final UserRepo userRepo;

  @Override
  public List<CryptoWalletResponse> getWalletsByUserId(Long userId) {
    Optional<User> optionalUser = userRepo.findById(userId);
    User user = optionalUser.orElseThrow(
        () -> new BaseException("User not found: " + userId, ErrorCode.USER_NOT_FOUND)
    );

    return cryptoWalletRepo.findByUser(user).stream()
        .map(cryptoWallet -> {
          CryptoWalletResponse response = new CryptoWalletResponse();
          response.setBtcUnit(cryptoWallet.getBtcUnit());
          response.setEthUnit(cryptoWallet.getEthUnit());
          response.setUsdtBalance(cryptoWallet.getUsdtBalance());
          response.setCreatedTime(cryptoWallet.getCreatedTime());
          return response;
        })
        .collect(Collectors.toList());
  }
}
