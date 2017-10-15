package xyz.lannt.constant;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum Market {
  BITTREX("bittrex"),
  COIN_EXCHANGE("coin-exchange"),
  ;

  @Getter
  private final String value;

  public static Market of(String lowercaseName) {
    return Stream.of(values()).filter(e -> StringUtils.equals(e.getValue(), lowercaseName))
        .findFirst()
        .orElseGet(null);
  }

  public static Market of(String lowercaseName, Supplier<RuntimeException> exceptionSupplier) {
    return Stream.of(values()).filter(e -> StringUtils.equals(e.getValue(), lowercaseName))
        .findFirst()
        .orElseThrow(exceptionSupplier);
  }
}
