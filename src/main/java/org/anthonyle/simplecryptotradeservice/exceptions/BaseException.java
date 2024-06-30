package org.anthonyle.simplecryptotradeservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
  protected ErrorCode error;

  public BaseException(String message) {
    super(message);
  }

  public BaseException(String message, ErrorCode error) {
    this(message);
    this.error = error;
  }
}
