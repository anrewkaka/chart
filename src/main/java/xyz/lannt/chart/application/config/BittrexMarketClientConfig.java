package xyz.lannt.chart.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import xyz.lannt.chart.application.client.BittrexMarketClient;
import xyz.lannt.chart.application.client.MarketClient;
import xyz.lannt.chart.application.client.MarketClientSetting;
import xyz.lannt.chart.application.property.BittrexMarketProperty;

@Configurable
public class BittrexMarketClientConfig {

  @Autowired
  private BittrexMarketProperty bittrexMarketProperty;

  @Bean
  private MarketClient marketClient() {
    return new BittrexMarketClient(MarketClientSetting.builder()
        .baseUrl(bittrexMarketProperty.getBaseUrl())
        .apiKey(bittrexMarketProperty.getApiKey())
        .build());
  }
}
