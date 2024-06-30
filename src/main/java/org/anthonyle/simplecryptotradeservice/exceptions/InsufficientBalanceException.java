package org.anthonyle.simplecryptotradeservice.exceptions;

public class InsufficientBalanceException extends BaseException {

  public InsufficientBalanceException(String message) {
    super(message);
  }

  public InsufficientBalanceException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }
}
