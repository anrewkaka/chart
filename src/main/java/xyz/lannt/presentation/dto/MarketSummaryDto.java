package xyz.lannt.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.lannt.domain.vo.CryptoValue;

@Data
@Builder
@AllArgsConstructor
public class MarketSummaryDto {

  public String name;

  public CryptoValue hight;

  public CryptoValue low;

  public CryptoValue volume;

  public CryptoValue last;

  public CryptoValue baseVolume;

  public String timestamp;

  public CryptoValue bid;

  public CryptoValue ask;

  public CryptoValue openBuyOrders;

  public CryptoValue openSellOrders;

  public CryptoValue prevDay;

  public String created;

  public String displayMarketName;

  public String getNameRemoveBase(String baseCurrency) {
    return name.replace("-", "").replace(baseCurrency, "");
  }
}
