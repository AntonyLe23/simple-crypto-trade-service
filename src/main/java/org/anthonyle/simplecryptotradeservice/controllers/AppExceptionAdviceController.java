package org.anthonyle.simplecryptotradeservice.controllers;

import org.anthonyle.simplecryptotradeservice.dto.ExceptionResponseDto;
import org.anthonyle.simplecryptotradeservice.exceptions.BaseException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class AppExceptionAdviceController {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {BaseException.class})
  public ResponseEntity<ExceptionResponseDto> handleBadRequestFunctionalException(final BaseException e) {
    log.error("Unexpected ApplicationException", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponseDto.builder()
            .code(e.getError())
            .message(e.getMessage())
            .build()
        );
  }
}
