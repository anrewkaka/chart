package xyz.lannt.chart.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import xyz.lannt.chart.constant.ExchangeStatus;
import xyz.lannt.chart.domain.vo.Fee;
import xyz.lannt.chart.domain.vo.Price;
import xyz.lannt.chart.domain.vo.Quantity;
import xyz.lannt.chart.infrastructure.entity.StockExchange;

@Builder
@AllArgsConstructor
public class Exchange {

  public Stock stock;

  public Product product;

  public LocalDateTime datetime;

  public Price buyPrice;

  public Quantity quantity;

  public Price target1;

  public Price target2;

  public Price target3;

  public Price lowPriceSell;

  public Price sellPrice;

  public Fee fee;

  public ExchangeStatus status;

  public static Exchange fromEntity(StockExchange entity) {
    return Exchange.builder()
        .stock(new Stock(entity.stock, entity.host, entity.apiKey, entity.secretKey))
        .product(new Product(entity.productName))
        .datetime(entity.datetime)
        .buyPrice(Price.create(entity.buyPrice))
        .quantity(Quantity.create(entity.quantity))
        .target1(Price.create(entity.target1))
        .target2(Price.create(entity.target2))
        .target3(Price.create(entity.target3))
        .lowPriceSell(Price.create(entity.lowPriceSell))
        .fee(Fee.create(entity.fee))
        .build();
  }
}
