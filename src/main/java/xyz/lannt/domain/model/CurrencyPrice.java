package xyz.lannt.domain.model;

import java.time.LocalDateTime;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.CryptoTimestamp;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.presentation.dto.CurrencyPriceDetailDto;
import xyz.lannt.presentation.dto.CurrencyPriceRegistrationDto;

public class CurrencyPrice {

  public static final String REDIS_KEY_DELIMITER = ":";

  @Getter
  private CryptoText name;

  @Getter
  private CryptoValue price;

  @Getter
  private CryptoTimestamp datetime;

  private CurrencyPrice() {
  }

  @Builder
  public CurrencyPrice(String name, String price, CryptoTimestamp datetime) {
    this.name = CryptoText.create(name);
    this.price = CryptoValue.create(price);
    this.datetime = datetime;
  }

  public CurrencyPrice(String name, CryptoValue price, CryptoTimestamp datetime) {
    this.name = CryptoText.create(name);
    this.price = price;
    this.datetime = datetime;
  }

  public CurrencyPrice(String name, CryptoValue price) {
    this.name = CryptoText.create(name);
    this.price = price;
    datetime = CryptoTimestamp.create(LocalDateTime.now());
  }

  public static CurrencyPrice fromDto(CurrencyPriceRegistrationDto dto) {
    CurrencyPrice price = new CurrencyPrice();
    price.name = CryptoText.create(dto.getCurrency());
    price.price = CryptoValue.create(dto.getPrice());
    price.datetime = CryptoTimestamp.now();
    return price;
  }

  public static CurrencyPrice fromEntrySet(Entry<String, CurrencyPrices> entry) {
    CurrencyPrice price = new CurrencyPrice();
    price.name = CryptoText.create(entry.getKey());
    price.price = entry.getValue().priceInAverage();
    price.datetime = CryptoTimestamp.now();
    return price;
  }
  
  public static String createKey(String exchange, String currency) {
    return String.join(REDIS_KEY_DELIMITER, "prices", exchange, currency);
  }

  public static String getCurrencyByKey(String key) {
    return StringUtils.split(key, REDIS_KEY_DELIMITER)[2];
  }

  public String getNameAsString() {
    return name.toString();
  }

  public CurrencyPriceDetailDto toDto() {
    return CurrencyPriceDetailDto.builder()
        .name(name)
        .price(price)
        .build();
  }
}
