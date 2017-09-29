package xyz.lannt.chart.application.client.response.bittrex;

import xyz.lannt.chart.application.client.response.MarketResponse;

public abstract class BittrexResponse implements MarketResponse {

  public Boolean success;

  public String message;
}
