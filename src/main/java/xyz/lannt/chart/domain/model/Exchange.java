package xyz.lannt.chart.domain.model;

import static xyz.lannt.chart.constant.ExchangeStatus.CLOSE;
import static xyz.lannt.chart.constant.ExchangeStatus.OPEN;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import xyz.lannt.chart.constant.ExchangeStatus;
import xyz.lannt.chart.domain.vo.Fee;
import xyz.lannt.chart.domain.vo.Price;
import xyz.lannt.chart.domain.vo.Quantity;
import xyz.lannt.chart.infrastructure.entity.StockExchange;
import xyz.lannt.chart.presentation.dto.ExchangeDto;

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
        .stock(new Stock(entity.stockId, entity.stock, entity.host, entity.apiKey, entity.secretKey))
        .product(new Product(entity.productId, entity.productName))
        .datetime(entity.datetime)
        .buyPrice(Price.create(entity.buyPrice))
        .quantity(Quantity.create(entity.quantity))
        .target1(Price.create(entity.target1))
        .target2(Price.create(entity.target2))
        .target3(Price.create(entity.target3))
        .lowPriceSell(Price.create(entity.lowPriceSell))
        .sellPrice(Price.create(entity.sellPrice))
        .fee(Fee.create(entity.fee))
        .status(ExchangeStatus.fromValue(entity.status))
        .build();
  }

  public boolean isOpen() {
    return OPEN.equals(status);
  }

  public boolean isClosed() {
    return CLOSE.equals(status);
  }

  public ExchangeDto toDto() {
    return ExchangeDto.builder()
        .stockId(String.valueOf(stock.getId()))
        .stock(stock.getName())
        .productId(String.valueOf(product.getId()))
        .product(product.getName())
        .datetime(datetime.format(DateTimeFormatter.ISO_DATE_TIME))
        .buyPrice(buyPrice.floatValue())
        .quantity(quantity.floatValue())
        .target1(target1.floatValue())
        .target2(target2.floatValue())
        .target3(target3.floatValue())
        .lowPriceSell(lowPriceSell.floatValue())
        .sellPrice(sellPrice.floatValue())
        .fee(fee.floatValue())
        .status(status.name())
        .build();
  }
}
