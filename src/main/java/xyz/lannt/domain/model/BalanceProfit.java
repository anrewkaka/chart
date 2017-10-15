package xyz.lannt.domain.model;

import java.util.Optional;

import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.exception.MarketClientException;
import xyz.lannt.presentation.dto.BalanceProfitDto;


public class BalanceProfit {

  private static double BITTREX_FEE = 0.25 / 100;

  private static final String ERROR_MESSAGE_MARKET_NOT_FOUND = "market not found!!";

  private static final String ERROR_MESSAGE_PRICE_NOT_FOUND = "prices not found!!";

  private String name;

  private CryptoValue amount;

  private CryptoValue buyPrice;

  private CryptoValue currentPrice;

  private CryptoValue profit;

  private CryptoValue profitInPercentage;

  public static BalanceProfit create(BittrexBalance balance, Optional<MarketSummary> market, CurrencyPrices prices) {
    if (!market.isPresent()) {
      throw new MarketClientException(ERROR_MESSAGE_MARKET_NOT_FOUND);
    }

    if (prices.isEmpty()) {
      throw new MarketClientException(ERROR_MESSAGE_PRICE_NOT_FOUND);
    }

    BalanceProfit balanceProfit = new BalanceProfit();
    balanceProfit.name = balance.getCurrency().toString();
    balanceProfit.amount = balance.getBalance();
    balanceProfit.buyPrice = prices.priceInAverage();
    balanceProfit.currentPrice = CryptoValue.create(market.get().getAsk());

    // profit = current - buy - exchange_fee
    balanceProfit.profit = balanceProfit.currentPrice.subtract(balanceProfit.buyPrice).multiply(CryptoValue.create(1 - BITTREX_FEE));
    // profitInPercentage = profit / current * 100
    balanceProfit.profitInPercentage = balanceProfit.profit.divide(balanceProfit.buyPrice).multiply(CryptoValue.create(100));

    return balanceProfit;
  }

  public BalanceProfitDto toDto() {
    return BalanceProfitDto.builder()
        .name(name)
        .amount(amount)
        .buyPrice(buyPrice)
        .currentPrice(currentPrice)
        .profit(profit)
        .profitInPercentage(profitInPercentage)
        .build();
  }
}
