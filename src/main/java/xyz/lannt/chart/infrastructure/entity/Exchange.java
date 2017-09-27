package xyz.lannt.chart.infrastructure.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class Exchange {

  @Column(name = "ID")
  public int id;

  @Column(name = "STCK_ID")
  public int stockId;

  @Column(name = "PRD_ID")
  public int productId;

  @Column(name = "DTTM")
  public LocalDateTime datetime;

  @Column(name = "BUY_PRC")
  public float buyPrice;

  @Column(name = "QTY")
  public float quantity;

  @Column(name = "TG1")
  public float target1;

  @Column(name = "TG2")
  public float target2;

  @Column(name = "TG3")
  public float target3;

  @Column(name = "LOW_PRC_SELL")
  public float lowPriceSell;

  @Column(name = "SELL_PRC")
  public float sellPrice;

  @Column(name = "FEE")
  public float fee;

  @Column(name = "STS")
  public String status;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
