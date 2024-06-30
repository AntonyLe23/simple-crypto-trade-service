package org.anthonyle.simplecryptotradeservice.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class HuobiResponse implements Serializable {

  private List<HuobiPrice> data;
  private String status;
}
