package xyz.lannt.infrastructure.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import xyz.lannt.infrastructure.entity.StockExchange;

@Repository
public class TestExchangeDao implements ExchangeDao {

  @Override
  public List<StockExchange> selectAll() {
    List<StockExchange> results = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      StockExchange se = new StockExchange();
      se.apiKey = "test_key_" + i;
      se.buyPrice = 0.00057 + i;
      se.datetime = LocalDateTime.now();
      se.fee = 0.5;
      se.host = "test_host_" + i;
      se.id = i;
      se.logicDeleteFlag = "0";
      se.lowPriceSell = 0.0005 + i;
      se.productId = i;
      se.productName = "BTC_LUN";
      se.quantity = 135.678 + i;
      se.secretKey = "test_secret_" + i;
      se.sellPrice = 0.0008 + i;
      se.status = "0";
      se.stockId = i;
      se.stock = "test_stock_" + i;
      se.target1 = 0.0006 + i;
      se.target2 = 0.0007 + i;
      se.target3 = 0.0008 + i;
      results.add(se);
    }

    for (int i = 5; i < 10; i++) {
      StockExchange se = new StockExchange();
      se.apiKey = "test_key_" + i;
      se.buyPrice = 0.00057 + i;
      se.datetime = LocalDateTime.now();
      se.fee = 0.5;
      se.host = "test_host_" + i;
      se.id = i;
      se.logicDeleteFlag = "0";
      se.lowPriceSell = 0.0005 + i;
      se.productId = i;
      se.productName = "BTC_LUN";
      se.quantity = 135.678 + i;
      se.secretKey = "test_secret_" + i;
      se.sellPrice = 0.0008 + i;
      se.status = "1";
      se.stockId = i;
      se.stock = "test_stock_" + i;
      se.target1 = 0.0006 + i;
      se.target2 = 0.0007 + i;
      se.target3 = 0.0008 + i;
      results.add(se);
    }

    return results;
  }

  @Override
  public Optional<StockExchange> select(String stockId, String productId) {
    StockExchange se = new StockExchange();
    se.apiKey = "test_key_";
    se.buyPrice = 0.00057;
    se.datetime = LocalDateTime.now();
    se.fee = 0.5;
    se.host = "test_host_";
    se.id = 1;
    se.logicDeleteFlag = "0";
    se.lowPriceSell = 0.0005;
    se.productId = Integer.parseInt(productId);
    se.productName = "BTC_LUN";
    se.quantity = 135.678;
    se.secretKey = "test_secret_";
    se.sellPrice = 0.0008;
    se.status = "0";
    se.stockId = Integer.parseInt(stockId);
    se.stock = "test_stock_";
    se.target1 = 0.0006;
    se.target2 = 0.0007;
    se.target3 = 0.0008;

    return Optional.of(se);
  }
}
