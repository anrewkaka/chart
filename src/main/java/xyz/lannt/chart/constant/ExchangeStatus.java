package xyz.lannt.chart.constant;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExchangeStatus {

  OPEN("1"), CLOSE("0"), ;

  @Getter
  private final String value;

  public static ExchangeStatus fromValue(String value) {
    return Stream.of(ExchangeStatus.values())
        .filter(e -> e.value.equals(value))
        .findFirst()
        .orElse(null);
  }
}
