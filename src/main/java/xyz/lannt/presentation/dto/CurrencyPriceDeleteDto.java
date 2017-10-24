package xyz.lannt.presentation.dto;

import lombok.Data;

@Data
public class CurrencyPriceDeleteDto {
  
  private String exchange;

  private String currency;
}
