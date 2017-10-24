package xyz.lannt.presentation.dto;

import lombok.Data;
import xyz.lannt.annotation.MarketRequestDto;

@Data
@MarketRequestDto
public class ExchangeSellDto {

  private String exchange;

  private String currency;

  private String rate;

  private String quantity;

  private String baseCurrency;
  
  private String market;
}
