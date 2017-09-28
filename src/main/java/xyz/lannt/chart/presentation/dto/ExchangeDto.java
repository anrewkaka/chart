package xyz.lannt.chart.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ExchangeDto {

  public String stockId;

  public String stock;

  public String productId;

  public String product;

  public String datetime;

  public float buyPrice;

  public float quantity;

  public float target1;

  public float target2;

  public float target3;

  public float lowPriceSell;

  public float sellPrice;

  public float fee;

  public String status;
}
