package org.anthonyle.simplecryptotradeservice.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.anthonyle.simplecryptotradeservice.dto.TradeRequest;
import org.anthonyle.simplecryptotradeservice.dto.TradeResult;
import org.anthonyle.simplecryptotradeservice.enums.TradeType;
import org.anthonyle.simplecryptotradeservice.exceptions.ErrorCode;
import org.anthonyle.simplecryptotradeservice.exceptions.InsufficientBalanceException;
import org.anthonyle.simplecryptotradeservice.exceptions.TradeException;
import org.anthonyle.simplecryptotradeservice.models.AggregatedPrice;
import org.anthonyle.simplecryptotradeservice.models.CryptoWallet;
import org.anthonyle.simplecryptotradeservice.models.TransactionDetail;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.anthonyle.simplecryptotradeservice.repositories.CryptoWalletRepo;
import org.anthonyle.simplecryptotradeservice.repositories.TransactionDetailRepo;
import org.anthonyle.simplecryptotradeservice.repositories.UserRepo;
import org.anthonyle.simplecryptotradeservice.services.AggregatedPriceService;
import org.anthonyle.simplecryptotradeservice.services.TradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

  private final UserRepo userRepo;
  private final CryptoWalletRepo cryptoWalletRepo;
  private final AggregatedPriceService aggregatedPriceService;
  private final TransactionDetailRepo transactionDetailRepo;

  @Override
  @Transactional
  public TradeResult executeTrade(TradeRequest tradeRequest) {
    Optional<User> userOpt = userRepo.findById(tradeRequest.getUserId());
    User user = userOpt.orElseThrow(() -> new TradeException("User not found", ErrorCode.USER_NOT_FOUND));

    Optional<CryptoWallet> cryptoWalletOpt = cryptoWalletRepo.findByIdAndUser(tradeRequest.getWalletId(), user);
    CryptoWallet cryptoWallet = cryptoWalletOpt.orElseThrow(() -> new TradeException("Wallet not found", ErrorCode.WALLET_NOT_FOUND));

    // Get best price
    AggregatedPrice bestPrice = aggregatedPriceService.getLatestBestAggregatedPricesByCryptoPairs(tradeRequest.getCryptoPair());
    if (Objects.isNull(bestPrice)) {
      throw new TradeException(
          String.format("Cannot find latest aggregated price for %s", tradeRequest.getCryptoPair().name()),
          ErrorCode.AGGREGATE_PRICE_NOT_FOUND
      );
    }

    // Process
    TransactionDetail detail = new TransactionDetail();
    switch (tradeRequest.getTradeType()) {
      case BUY:
        detail = this.buyTransactionProcess(tradeRequest, cryptoWallet, bestPrice);
        break;
      case SELL:
        detail = this.sellTransactionProcess(tradeRequest, cryptoWallet, bestPrice);
        break;
    }

    // Last check
    if (Objects.isNull(cryptoWallet)
        || cryptoWallet.getUsdtBalance().compareTo(BigDecimal.ZERO) < 0
        || cryptoWallet.getBtcUnit().compareTo(BigDecimal.ZERO) < 0
        || cryptoWallet.getEthUnit().compareTo(BigDecimal.ZERO) < 0) {
      throw new TradeException("Something went wrong", ErrorCode.INTERNAL_TRANSACTION_ERROR);
    }

    return this.generateTradeResult(detail);
  }

  @Override
  public List<TradeResult> getTradesByUser(Long userId) {
    Optional<User> userOpt = userRepo.findById(userId);
    User user = userOpt.orElseThrow(() -> new TradeException("User not found", ErrorCode.USER_NOT_FOUND));

    return transactionDetailRepo.findByUser(user).stream()
        .map(this::generateTradeResult)
        .collect(Collectors.toList());
  }

  private BigDecimal getPricePerUnit(TradeType tradeType, AggregatedPrice bestPrice) {
    switch (tradeType) {
      case BUY:
        return bestPrice.getAskPrice();
      case SELL:
        return bestPrice.getBidPrice();
      default:
        return null;
    }
  }

  private TransactionDetail buyTransactionProcess(TradeRequest tradeRequest, CryptoWallet cryptoWallet, AggregatedPrice bestPrice) {
    BigDecimal pricePerUnit = getPricePerUnit(tradeRequest.getTradeType(), bestPrice);
    if (Objects.isNull(pricePerUnit)) {
      throw new TradeException(
          String.format("Cannot find latest aggregated price for %s", tradeRequest.getCryptoPair().name()),
          ErrorCode.AGGREGATE_PRICE_NOT_FOUND
      );
    }

    BigDecimal expectedAmount = pricePerUnit.multiply(tradeRequest.getUnit());
    if (expectedAmount.compareTo(cryptoWallet.getUsdtBalance()) < 0) {
      TransactionDetail detail = new TransactionDetail();
      BigDecimal updatedBalance = cryptoWallet.getUsdtBalance().subtract(expectedAmount);
      cryptoWallet.setUsdtBalance(updatedBalance);

      switch (tradeRequest.getCryptoPair()) {
        case BTCUSDT:
          BigDecimal updatedBtcUnit = cryptoWallet.getBtcUnit().add(tradeRequest.getUnit());
          cryptoWallet.setBtcUnit(updatedBtcUnit);
          break;
        case ETHUSDT:
          BigDecimal updatedEthUnit = cryptoWallet.getEthUnit().add(tradeRequest.getUnit());
          cryptoWallet.setEthUnit(updatedEthUnit);
          break;
      }
      cryptoWalletRepo.save(cryptoWallet);

      detail.setUser(cryptoWallet.getUser());
      detail.setCryptoWallet(cryptoWallet);
      detail.setTradeType(tradeRequest.getTradeType());
      detail.setCryptoPair(tradeRequest.getCryptoPair());
      detail.setPricePerUnit(pricePerUnit);
      detail.setQuantity(tradeRequest.getUnit());
      detail.setTotalTransactionAmount(expectedAmount);
      return transactionDetailRepo.save(detail);
    } else {
      throw new InsufficientBalanceException("Not enough balance to buy", ErrorCode.REMAIN_BALANCE_NOT_ENOUGH);
    }

  }

  private TransactionDetail sellTransactionProcess(TradeRequest tradeRequest, CryptoWallet cryptoWallet, AggregatedPrice bestPrice) {
    BigDecimal pricePerUnit = getPricePerUnit(tradeRequest.getTradeType(), bestPrice);
    if (Objects.isNull(pricePerUnit)) {
      throw new TradeException(
          String.format("Cannot find latest aggregated price for %s", tradeRequest.getCryptoPair().name()),
          ErrorCode.AGGREGATE_PRICE_NOT_FOUND
      );
    }

    TransactionDetail detail = new TransactionDetail();
    detail.setUser(cryptoWallet.getUser());
    detail.setCryptoPair(tradeRequest.getCryptoPair());
    detail.setTradeType(tradeRequest.getTradeType());
    detail.setCryptoWallet(cryptoWallet);
    detail.setPricePerUnit(pricePerUnit);
    detail.setQuantity(tradeRequest.getUnit());

    switch (tradeRequest.getCryptoPair()) {
      case BTCUSDT:
        // Verify btc quantity
        if (cryptoWallet.getBtcUnit().compareTo(tradeRequest.getUnit()) >= 0) {
          BigDecimal updatedBtcUnit = cryptoWallet.getBtcUnit().subtract(tradeRequest.getUnit());
          cryptoWallet.setBtcUnit(updatedBtcUnit);

          BigDecimal gainAmount = pricePerUnit.multiply(tradeRequest.getUnit());
          BigDecimal newBalance = cryptoWallet.getUsdtBalance().add(gainAmount);
          cryptoWallet.setUsdtBalance(newBalance);
          detail.setTotalTransactionAmount(gainAmount);
          cryptoWalletRepo.save(cryptoWallet);
        } else {
          throw new TradeException("Btc unit not enough", ErrorCode.REMAIN_UNIT_NOT_ENOUGH);
        }
        break;
      case ETHUSDT:
        if (cryptoWallet.getEthUnit().compareTo(tradeRequest.getUnit()) >= 0) {
          BigDecimal updatedEthUnit = cryptoWallet.getEthUnit().subtract(tradeRequest.getUnit());
          cryptoWallet.setEthUnit(updatedEthUnit);

          BigDecimal gainAmount = pricePerUnit.multiply(tradeRequest.getUnit());
          BigDecimal newBalance = cryptoWallet.getUsdtBalance().add(gainAmount);
          cryptoWallet.setUsdtBalance(newBalance);
          detail.setTotalTransactionAmount(gainAmount);
          cryptoWalletRepo.save(cryptoWallet);
        } else {
          throw new TradeException("Eth unit not enough", ErrorCode.REMAIN_UNIT_NOT_ENOUGH);
        }
        break;
    }

    return transactionDetailRepo.save(detail);
  }

  private TradeResult generateTradeResult(TransactionDetail transactionDetail) {
    TradeResult tradeResult = new TradeResult();
    tradeResult.setCryptoWalletId(transactionDetail.getCryptoWallet().getId());
    tradeResult.setTradeType(transactionDetail.getTradeType());
    tradeResult.setCryptoPair(transactionDetail.getCryptoPair());
    tradeResult.setUnit(transactionDetail.getQuantity());
    tradeResult.setPricePerUnit(transactionDetail.getPricePerUnit());
    tradeResult.setTotalExecutedAmount(transactionDetail.getTotalTransactionAmount());
    tradeResult.setCreatedTime(transactionDetail.getCreatedDateTime());
    return tradeResult;
  }

}
