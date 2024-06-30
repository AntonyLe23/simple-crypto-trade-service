package org.anthonyle.simplecryptotradeservice.exceptions;

public class TradeException extends BaseException {

  public TradeException(String message) {
    super(message);
  }

  public TradeException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }
}
