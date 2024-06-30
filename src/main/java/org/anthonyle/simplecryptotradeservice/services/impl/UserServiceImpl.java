package org.anthonyle.simplecryptotradeservice.services.impl;

import org.anthonyle.simplecryptotradeservice.models.CryptoWallet;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.anthonyle.simplecryptotradeservice.repositories.CryptoWalletRepo;
import org.anthonyle.simplecryptotradeservice.repositories.UserRepo;
import org.anthonyle.simplecryptotradeservice.services.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepository;
  private final CryptoWalletRepo cryptoWalletRepo;

  @Override
  public User registerFakeUser() {
    User user = new User();
    user.setUsername("test_user");
    user = userRepository.save(user);
    this.autoCreateUserWallet(user);
    return user;
  }

  private CryptoWallet autoCreateUserWallet(User user) {
    CryptoWallet cryptoWallet = new CryptoWallet();
    cryptoWallet.setUser(user);
    return cryptoWalletRepo.save(cryptoWallet);
  }
}
