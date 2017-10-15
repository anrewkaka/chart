package xyz.lannt.infrastructure.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import xyz.lannt.infrastructure.entity.StockExchange;

@Dao
public interface ExchangeDao {

  @Select
  public List<StockExchange> selectAll();

  @Select
  public Optional<StockExchange> select(String stockId, String productId);
}
