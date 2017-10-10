package xyz.lannt.chart.presentation.dto;

import lombok.Data;

@Data
public class ExchangeSellDto {

  private String name;

  private String price;

  private String quantity;

  private String baseCurrency;
}
