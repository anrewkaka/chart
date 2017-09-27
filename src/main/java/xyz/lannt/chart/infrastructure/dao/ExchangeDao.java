package xyz.lannt.chart.infrastructure.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import xyz.lannt.chart.infrastructure.entity.StockExchange;

@Dao
public interface ExchangeDao {

  @Select
  public List<StockExchange> selectAll();

  @Select
  public StockExchange select(String productId);
}
