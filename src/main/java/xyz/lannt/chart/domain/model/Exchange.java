package xyz.lannt.chart.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import xyz.lannt.chart.constant.ExchangeStatus;

public class Exchange {

  public Stock stock;

  public Product product;

  public LocalDateTime datetime;

  public BigDecimal buyPrice;

  public BigDecimal quantity;

  public BigDecimal target1;

  public BigDecimal target2;

  public BigDecimal target3;

  public BigDecimal lowPriceSell;

  public BigDecimal sellPrice;

  public BigDecimal fee;

  public ExchangeStatus status;
}
