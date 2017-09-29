package xyz.lannt.chart.application.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import xyz.lannt.chart.application.client.request.MarketRequest;
import xyz.lannt.chart.application.client.response.MarketResponse;
import xyz.lannt.chart.application.exception.ChartException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface MarketClient {

  MarketClientSetting getSetting();

  FieldNamingPolicy getGsonFieldNamingPolicy();

  default <T> T request(String uri, HttpMethod method, MarketRequest request, Class<T> responseType) {

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
    Gson gson = new GsonBuilder().setFieldNamingPolicy(getGsonFieldNamingPolicy()).create();
    GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
    messageConverter.setGson(gson);
    restTemplate.getMessageConverters().add(messageConverter);

    String url = getSetting().getBaseUrl() + uri;

    if (HttpMethod.GET.equals(method)) {
      try {
        if (!ObjectUtils.isEmpty(request)) {
          url += "?" + request.toQueryParam();
        }
      } catch (IllegalAccessException e) {
        throw new ChartException(e);
      }
      return restTemplate.getForObject(url, responseType);
    }
    return restTemplate.postForObject(url, gson.toJson(request), responseType);
  }

  MarketResponse getMarkets(MarketRequest request);

  MarketResponse getCurrencies(MarketRequest request);

  MarketResponse getTicker(MarketRequest request);

  MarketResponse getSummary(MarketRequest request);

  MarketResponse getSummaries(MarketRequest request);

  MarketResponse getOrderBook(MarketRequest request);

  MarketResponse getMarketHistory(MarketRequest request);

  MarketResponse getOrder(MarketRequest request);

  MarketResponse getOrderHistory(MarketRequest request);

  MarketResponse getOpenOrders(MarketRequest request);

  MarketResponse getBalance(MarketRequest request);

  MarketResponse getBalances(MarketRequest request);

  MarketResponse buy(MarketRequest request);

  MarketResponse sell(MarketRequest request);

  MarketResponse cancel(MarketRequest request);
}
