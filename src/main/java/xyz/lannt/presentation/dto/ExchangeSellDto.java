package xyz.lannt.presentation.dto;

import lombok.Data;
import xyz.lannt.annotation.MarketRequestDto;

@Data
@MarketRequestDto
public class ExchangeSellDto {

  private String exchange;

  private String name;

  private String price;

  private String quantity;

  private String baseCurrency;
}
