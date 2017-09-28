package xyz.lannt.chart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {

  private int id;

  private String name;

  private String host;

  private String apiKey;

  private String secretKey;

}
