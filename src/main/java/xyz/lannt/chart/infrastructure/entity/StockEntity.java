package xyz.lannt.chart.infrastructure.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

@Entity
@Table(name = "STOCK")
public class StockEntity {

  @Column(name = "ID")
  public Integer id;

  @Column(name = "NM")
  public String name;

  @Column(name = "HST")
  public String host;

  @Column(name = "API_KEY")
  public String apiKey;

  @Column(name = "SCR_KEY")
  public String secretKey;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
