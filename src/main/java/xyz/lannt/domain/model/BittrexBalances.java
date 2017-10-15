package xyz.lannt.domain.model;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import xyz.lannt.domain.vo.Products;
import xyz.lannt.market.response.bittrex.BittrexBalancesResponse;
import xyz.lannt.presentation.dto.BalanceDto;

@AllArgsConstructor
public class BittrexBalances {

  private List<BittrexBalance> values;

  public static BittrexBalances fromResponse(BittrexBalancesResponse response) {
    return response.getResult().stream()
        .map(BittrexBalance::fromResult)
        .collect(collectingAndThen(toList(), BittrexBalances::new));
  }

  public Stream<BittrexBalance> stream() {
    return values.stream();
  }

  public String[] getCurrencies() {
    return values.stream()
        .map(e -> e.getCurrency().toString())
        .toArray(size -> new String[size]);
  }

  public Products getMarketNames(String baseCurrency) {
    return values.stream()
        .filter(e -> e.nonBaseCurrency(baseCurrency))
        .map(e -> e.getMarketName(baseCurrency))
        .collect(collectingAndThen(toList(), Products::new));
  }

  public Optional<BittrexBalance> find(String currency) {
    return values.stream()
        .filter(e -> StringUtils.equals(e.getCurrency().toString(), currency))
        .findFirst();
  }

  public BittrexBalances removeEmpty() {
    return values.stream()
        .filter(BittrexBalance::isGreaterThanZero)
        .collect(collectingAndThen(toList(), BittrexBalances::new));
  }

  public List<BalanceDto> toDtoes() {
    return values.stream()
        .map(BittrexBalance::toDto)
        .collect(Collectors.toList());
  }
}
