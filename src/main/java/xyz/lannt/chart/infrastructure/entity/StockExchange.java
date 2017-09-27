package xyz.lannt.chart.infrastructure.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class StockExchange {

  @Column(name = "ID")
  public Integer id;

  @Column(name = "STCK_NM")
  public String stock;

  @Column(name = "HST")
  public String host;

  @Column(name = "API_KEY")
  public String apiKey;

  @Column(name = "SCR_KEY")
  public String secretKey;

  @Column(name = "PRD_NM")
  public String productName;

  @Column(name = "DTTM")
  public LocalDateTime datetime;

  @Column(name = "BUY_PRC")
  public Float buyPrice;

  @Column(name = "QTY")
  public Float quantity;

  @Column(name = "TG1")
  public Float target1;

  @Column(name = "TG2")
  public Float target2;

  @Column(name = "TG3")
  public Float target3;

  @Column(name = "LOW_PRC_SELL")
  public Float lowPriceSell;

  @Column(name = "SELL_PRC")
  public Float sellPrice;

  @Column(name = "FEE")
  public Float fee;

  @Column(name = "STS")
  public String status;

  @Column(name = "LGC_DEL_FLG")
  public String logicDeleteFlag;
}
