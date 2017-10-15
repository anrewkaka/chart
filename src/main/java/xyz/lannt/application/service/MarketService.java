package xyz.lannt.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.lannt.constant.Market;
import xyz.lannt.domain.model.MarketComapre;
import xyz.lannt.domain.model.MarketSummaries;
import xyz.lannt.domain.vo.Products;
import xyz.lannt.exception.MarketClientException;
import xyz.lannt.market.client.MarketClientFactory;
import xyz.lannt.market.response.bittrex.BittrexMarketSummariesResponse;
import xyz.lannt.presentation.dto.MarketCompareDto;
import xyz.lannt.presentation.dto.MarketSummaryDto;

@Service
public class MarketService {

  private static final String MARKET_NOTFOUND_MESSAGE = "market not found!!";

  @Autowired
  private MarketClientFactory marketClientFactory;

  public List<MarketSummaryDto> getSummaries(String exchange, Products products) {
    return this.getSummaries(exchange)
        .find(products)
        .toDtoes();
  }

  public MarketSummaries getSummaries(String exchange) {
    // TODO: remove casting
    return MarketSummaries.fromResponse((BittrexMarketSummariesResponse)marketClientFactory.getClient(Market.of(exchange)).getMarketSummaries());
  }

  public MarketSummaryDto getSummary(String exchange, String market) {
    return findAll(exchange).find(market)
        .orElseThrow(() -> new MarketClientException(MARKET_NOTFOUND_MESSAGE))
        .toDto();
  }

  public MarketCompareDto getCompare(String exchange, String base, String source, String target) {
    MarketSummaries marketSummaries = findAll(exchange);
    MarketComapre marketCompare = MarketComapre.builder()
        .base(marketSummaries.find(base).orElseThrow(() -> new MarketClientException(MARKET_NOTFOUND_MESSAGE)))
        .source(marketSummaries.find(source).orElseThrow(() -> new MarketClientException(MARKET_NOTFOUND_MESSAGE)))
        .target(marketSummaries.find(target).orElseThrow(() -> new MarketClientException(MARKET_NOTFOUND_MESSAGE)))
        .build();
    return marketCompare.calculate();
  }

  private MarketSummaries findAll(String exchange) {
    // TODO: remove casting
    return MarketSummaries.fromResponse((BittrexMarketSummariesResponse)marketClientFactory.getClient(Market.of(exchange)).getMarketSummaries());
  }
}
