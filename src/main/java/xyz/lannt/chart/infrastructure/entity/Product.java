package xyz.lannt.chart.infrastructure.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class Product {

  @Column(name = "ID")
  public String id;

  @Column(name = "NM")
  public String name;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
