package xyz.lannt.chart.application.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpMethod;

import xyz.lannt.chart.application.client.request.MarketRequest;
import xyz.lannt.chart.application.client.response.MarketResponse;
import xyz.lannt.chart.application.client.response.bittrex.BittrexTickerResponse;

import com.google.gson.FieldNamingPolicy;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BittrexMarketClient implements MarketClient {

  @Getter
  private MarketClientSetting setting;

  @Override
  public FieldNamingPolicy getGsonFieldNamingPolicy() {
    return FieldNamingPolicy.UPPER_CAMEL_CASE;
  }

  @Override
  public MarketResponse getMarkets(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getCurrencies(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getTicker(MarketRequest request) {
    return request("public/getTicker", HttpMethod.GET, request, BittrexTickerResponse.class);
  }

  @Override
  public MarketResponse getSummary(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getSummaries(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getOrderBook(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getMarketHistory(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getOrder(MarketRequest request) {
    return null;
  }

  @Override
  public MarketResponse getOrderHistory(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse getOpenOrders(MarketRequest request) {
    return request("account/getbalances", HttpMethod.GET, request, BittrexTickerResponse.class);
  }

  @Override
  public BittrexTickerResponse getBalance(MarketRequest request) {
    return request("account/getbalances", HttpMethod.GET, request, BittrexTickerResponse.class);
  }

  @Override
  public MarketResponse getBalances(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse buy(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse sell(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MarketResponse cancel(MarketRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

}
