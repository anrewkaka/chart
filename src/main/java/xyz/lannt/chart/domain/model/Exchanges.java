package xyz.lannt.chart.domain.model;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import xyz.lannt.chart.infrastructure.entity.StockExchange;

@AllArgsConstructor
public class Exchanges {

  private List<Exchange> values;

  public static Exchanges fromEntities(List<StockExchange> entities) {
    return entities.stream()
        .map(Exchange::fromEntity)
        .collect(Collectors.collectingAndThen(Collectors.toList(), Exchanges::new));
  }
}
