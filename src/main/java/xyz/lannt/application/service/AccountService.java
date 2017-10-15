package xyz.lannt.application.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.lannt.constant.Market;
import xyz.lannt.domain.model.BalanceProfit;
import xyz.lannt.domain.model.BittrexBalances;
import xyz.lannt.domain.model.CurrencyPrices;
import xyz.lannt.domain.model.MarketSummaries;
import xyz.lannt.exception.MarketClientException;
import xyz.lannt.infrastructure.repository.ExchangeRepository;
import xyz.lannt.market.client.MarketClientFactory;
import xyz.lannt.market.response.bittrex.BittrexBalancesResponse;
import xyz.lannt.presentation.dto.BalanceDto;
import xyz.lannt.presentation.dto.BalanceProfitDto;

@Service
public class AccountService {

  private static final String ERROR_MESSAGE_CURRENCY_NOT_FOUND = "currency not found!!";
 
  @Autowired
  private MarketClientFactory marketClientFactory;

  @Autowired
  private MarketService marketService;

  @Autowired
  private ExchangeRepository exchangeRepository;

  public List<BalanceDto> getBalances(String exchange) {
    // TODO: remove casting
    return BittrexBalances.fromResponse((BittrexBalancesResponse)marketClientFactory.getClient(Market.of(exchange)).getBalances())
        .removeEmpty()
        .toDtoes();
  }

  public BalanceDto getBalance(String exchange, String currency) {
    // TODO: remove casting
    return BittrexBalances.fromResponse((BittrexBalancesResponse)marketClientFactory.getClient(Market.of(exchange)).getBalances())
        .find(currency)
        .orElseThrow(() -> new MarketClientException(ERROR_MESSAGE_CURRENCY_NOT_FOUND))
        .toDto();
  }

  public List<BalanceProfitDto> getProfit(String exchange, String baseCurrency) {
    BittrexBalances balances = BittrexBalances.fromResponse((BittrexBalancesResponse)marketClientFactory.getClient(Market.of(exchange)).getBalances()).removeEmpty();
    MarketSummaries markets = marketService.getSummaries(exchange).find(balances.getMarketNames(baseCurrency));
    CurrencyPrices currencyPrices = exchangeRepository.multiGet("bittrex", balances.getCurrencies());

    return balances.removeEmpty().stream()
        .filter(e -> !e.getCurrency().toString().equals(baseCurrency.equals("BTC") ? "USDT" : "BTC"))
        .filter(e -> e.nonBaseCurrency(baseCurrency))
        .map(e -> {
          String marketName = e.getMarketName(baseCurrency);
          return BalanceProfit.create(e, markets.find(marketName), currencyPrices.find(e.getCurrency()));
        })
        .map(BalanceProfit::toDto)
        .collect(toList());

  }
}
