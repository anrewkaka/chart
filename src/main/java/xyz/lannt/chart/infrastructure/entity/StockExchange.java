package xyz.lannt.chart.infrastructure.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class StockExchange {

  @Column(name = "ID")
  public Integer id;

  @Column(name = "STCK_ID")
  public Integer stockId;

  @Column(name = "STCK_NM")
  public String stock;

  @Column(name = "HST")
  public String host;

  @Column(name = "API_KEY")
  public String apiKey;

  @Column(name = "SCR_KEY")
  public String secretKey;

  @Column(name = "PRD_ID")
  public Integer productId;

  @Column(name = "PRD_NM")
  public String productName;

  @Column(name = "DTTM")
  public LocalDateTime datetime;

  @Column(name = "BUY_PRC")
  public Double buyPrice;

  @Column(name = "QTY")
  public Double quantity;

  @Column(name = "TG1")
  public Double target1;

  @Column(name = "TG2")
  public Double target2;

  @Column(name = "TG3")
  public Double target3;

  @Column(name = "LOW_PRC_SELL")
  public Double lowPriceSell;

  @Column(name = "SELL_PRC")
  public Double sellPrice;

  @Column(name = "FEE")
  public Double fee;

  @Column(name = "STS")
  public String status;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
