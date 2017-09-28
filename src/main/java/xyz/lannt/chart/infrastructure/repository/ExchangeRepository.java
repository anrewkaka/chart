package xyz.lannt.chart.infrastructure.repository;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.lannt.chart.application.exception.ChartException;
import xyz.lannt.chart.domain.model.Exchange;
import xyz.lannt.chart.domain.model.Exchanges;
import xyz.lannt.chart.infrastructure.dao.ExchangeDao;

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
}
