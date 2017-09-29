package xyz.lannt.chart.application.property;

import lombok.Data;

import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;

@Data
@PropertyMapping("bittrex")
public class BittrexMarketProperty {

  private String baseUrl;
  
  private String apiKey;
}
