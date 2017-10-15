package xyz.lannt.infrastructure.repository;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static xyz.lannt.domain.model.CurrencyPrice.REDIS_KEY_DELIMITER;
import static xyz.lannt.domain.model.CurrencyPrice.createKey;
import static xyz.lannt.domain.model.CurrencyPrice.getCurrencyByKey;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import xyz.lannt.domain.model.CurrencyPrice;
import xyz.lannt.domain.model.CurrencyPrices;
import xyz.lannt.domain.model.Exchange;
import xyz.lannt.domain.model.Exchanges;
import xyz.lannt.domain.vo.CryptoTimestamp;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.exception.ChartException;
import xyz.lannt.infrastructure.dao.ExchangeDao;

@Repository
public class ExchangeRepository {

  @Autowired
  private ExchangeDao exchangeDao;

  public Exchanges findOpen() {
    return exchangeDao.selectAll().stream()
        .map(Exchange::fromEntity)
        .filter(Exchange::isOpen)
        .collect(collectingAndThen(toList(), Exchanges::new));
  }

  public Exchanges findClosed() {
    return exchangeDao.selectAll().stream()
        .map(Exchange::fromEntity)
        .filter(Exchange::isClosed)
        .collect(collectingAndThen(toList(), Exchanges::new));
  }

  public Exchange find(String stockId, String productId) {
    return exchangeDao.select(stockId, productId)
        .map(Exchange::fromEntity)
        .orElseThrow(() -> new ChartException());
  }
  
  @Autowired
  private StringRedisTemplate redisTemplate;

  public CurrencyPrices multiGet(String exchange, String ... currencies) {
    return Arrays.asList(currencies).stream()
        .map(e -> get(exchange, e))
        .reduce((e1, e2) -> e1.merge(e2))
        .get();
  }

  public CurrencyPrices get(String exchange, String currency) {
    return redisTemplate.opsForHash().entries(createKey(exchange, currency)).entrySet().stream()
        .map(e -> new CurrencyPrice(currency, CryptoValue.create(e.getValue()), CryptoTimestamp.create(e.getKey())))
        .collect(collectingAndThen(toList(), CurrencyPrices::new));
  }

  public CurrencyPrices get(String exchange) {
    return redisTemplate.keys(String.join(REDIS_KEY_DELIMITER, "prices", exchange, "*")).stream()
        .map(e -> {
          return this.get(exchange, getCurrencyByKey(e));
        })
        .reduce((e1, e2) -> e1.merge(e2))
        .orElseGet(CurrencyPrices::new);
  }

  public void set(String exchange, CurrencyPrice value) {
    redisTemplate.opsForHash().put(createKey(exchange, value.getName().toString()), value.getDatetime().toString(), value.getPrice().toString());
  }

  public void delete(String exchange, String currency) {
    get(exchange, currency).stream()
      .forEach(e -> {
        redisTemplate.opsForHash().delete(createKey(exchange, currency), e.getDatetime().toString());
      });
  }
}
