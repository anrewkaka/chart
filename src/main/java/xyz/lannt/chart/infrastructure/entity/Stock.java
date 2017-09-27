package xyz.lannt.chart.infrastructure.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class Stock {

  @Column(name = "ID")
  private String id;

  @Column(name = "NM")
  private String name;

  @Column(name = "HST")
  private String host;

  @Column(name = "API_KEY")
  private String apiKey;

  @Column(name = "SCR_KEY")
  private String secretKey;

  @Column(name = "LGC_DEL_FLG")
  private String logicDeleteFlag;
}
