package xyz.lannt.chart.domain.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import xyz.lannt.chart.presentation.dto.ExchangeDto;

@AllArgsConstructor
public class Exchanges {

  private List<Exchange> values;

  public Exchanges() {
    values = new ArrayList<Exchange>();
  }


  public List<ExchangeDto> toDtoes() {
    return values.stream()
        .map(Exchange::toDto)
        .collect(toList());
  }

  public Exchanges add(Exchange exchange) {
    values.add(exchange);
    return this;
  }
}
