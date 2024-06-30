package org.anthonyle.simplecryptotradeservice.dto;

import org.anthonyle.simplecryptotradeservice.exceptions.ErrorCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponseDto {

  private String message;
  private ErrorCode code;
}
