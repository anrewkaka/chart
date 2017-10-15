package xyz.lannt.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.lannt.annotation.MarketResponseName;
import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.market.response.bittrex.BittrexResult;
import xyz.lannt.presentation.dto.BalanceDto;

@NoArgsConstructor
public class BittrexBalance {

  @Getter
  @MarketResponseName("Currency")
  private CryptoText currency;

  @Getter
  @MarketResponseName("Balance")
  private CryptoValue balance;

  @MarketResponseName("Available")
  private CryptoValue available;

  @MarketResponseName("Pending")
  private CryptoValue pending;

  @MarketResponseName("CryptoAddress")
  private CryptoText address;

  public static BittrexBalance fromResult(BittrexResult result) {
    return result.toModel(BittrexBalance.class);
  }

  public String getMarketName(String baseCurrency) {
    return String.join("-", baseCurrency, currency.toString());
  }

  public boolean nonBaseCurrency(String baseCurrency) {
    return !StringUtils.equals(currency.toString(), baseCurrency);
  }

  public BalanceDto toDto() {
    return BalanceDto.builder()
        .currency(currency.toString())
        .balance(balance)
        .available(available)
        .address(address.toString())
        .build();
  }

  public boolean isGreaterThanZero() {
    return balance.isGreaterThanZero();
  }
}
