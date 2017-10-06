package xyz.lannt.chart.application.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bittrex-client")
public class BittrexMarketProperty {

  private String url;
}
