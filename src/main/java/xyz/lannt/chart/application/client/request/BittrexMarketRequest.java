package xyz.lannt.chart.application.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import xyz.lannt.chart.application.client.annotation.MarketQueryParam;

@Builder
@AllArgsConstructor
public class BittrexMarketRequest implements MarketRequest {

  @MarketQueryParam("apiKey")
  private String apiKey;

  @MarketQueryParam("market")
  private String market;

  public BittrexMarketRequest(String apiKey) {
    this.apiKey = apiKey;
  }
}
