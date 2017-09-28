package xyz.lannt.chart.constant;

import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.lannt.chart.application.exception.ChartException;

@AllArgsConstructor
public enum ExchangeStatus {

  OPEN("1"), CLOSE("0"), ;

  @Getter
  private final String value;

  public static ExchangeStatus of(String name, Supplier<ChartException> exceptionSupplier) {
    return Stream.of(ExchangeStatus.values())
        .filter(e -> e.name().toLowerCase(Locale.getDefault()).equals(name))
        .findFirst()
        .orElseThrow(exceptionSupplier);
  }

  public static ExchangeStatus fromValue(String value) {
    return Stream.of(ExchangeStatus.values())
        .filter(e -> e.value.equals(value))
        .findFirst()
        .orElse(null);
  }
}
