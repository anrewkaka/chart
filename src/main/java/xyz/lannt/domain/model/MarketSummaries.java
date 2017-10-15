package xyz.lannt.domain.model;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.Products;
import xyz.lannt.market.response.bittrex.BittrexMarketSummariesResponse;
import xyz.lannt.presentation.dto.MarketSummaryDto;

@AllArgsConstructor
public class MarketSummaries {

  private List<MarketSummary> values;

  public static MarketSummaries fromResponse(BittrexMarketSummariesResponse response) {
    return response.getResult().stream()
        .map(MarketSummary::fromResult)
        .collect(collectingAndThen(toList(), MarketSummaries::new));
  }

  public Optional<MarketSummary> find(String market) {
    return values.stream()
        .filter(e -> StringUtils.equals(e.getName().toString(), market))
        .findFirst();
  }

  public MarketSummaries find(Products products) {
    if (ObjectUtils.isEmpty(products)) {
      return this;
    }

    return values.stream()
        .filter(e -> products.contains(e.getName().toString()))
        .collect(collectingAndThen(toList(), MarketSummaries::new));
  }

  public List<MarketSummaryDto> toDtoes() {
    return values.stream()
        .map(MarketSummary::toDto)
        .collect(Collectors.toList());
  }

  public Products getNames() {
    return values.stream()
        .map(MarketSummary::getName)
        .map(CryptoText::toString)
        .collect(collectingAndThen(toList(), Products::new));
  }
}
