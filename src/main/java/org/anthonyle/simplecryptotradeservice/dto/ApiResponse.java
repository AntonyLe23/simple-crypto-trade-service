package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApiResponse<T> implements Serializable {

  private T data;
  private String message;
}
