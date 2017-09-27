package xyz.lannt.chart.infrastructure.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

  @Column(name = "ID")
  public Integer id;

  @Column(name = "NM")
  public String name;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
