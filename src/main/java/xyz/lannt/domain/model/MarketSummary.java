package xyz.lannt.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.lannt.annotation.MarketResponseName;
import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.CryptoTimestamp;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.market.response.bittrex.BittrexResult;
import xyz.lannt.presentation.dto.MarketSummaryDto;

@NoArgsConstructor
public class MarketSummary {

  @Getter
  @MarketResponseName("MarketName")
  private CryptoText name;

  @MarketResponseName("Hight")
  private CryptoValue hight;

  @MarketResponseName("Low")
  private CryptoValue low;

  @MarketResponseName("Volume")
  private CryptoValue volume;

  @Getter
  @MarketResponseName("Last")
  private CryptoValue last;

  @MarketResponseName("BaseVolume")
  private CryptoValue baseVolume;

  @MarketResponseName("TimeStamp")
  private CryptoTimestamp timestamp;

  @Getter
  @MarketResponseName("Bid")
  private CryptoValue bid;

  @Getter
  @MarketResponseName("Ask")
  private CryptoValue ask;

  @MarketResponseName("OpenBuyOrders")
  private CryptoValue openBuyOrders;

  @MarketResponseName("OpenSellOrders")
  private CryptoValue openSellOrders;

  @MarketResponseName("PrevDay")
  private CryptoValue prevDay;

  @MarketResponseName("Created")
  private CryptoTimestamp created;

  @MarketResponseName("DisplayMarketName")
  private CryptoText displayMarketName;

  public static MarketSummary fromResult (BittrexResult result) {
    return result.toModel(MarketSummary.class);
  }

  public MarketSummaryDto toDto() {
    return MarketSummaryDto.builder()
        .name(name.toString())
        .hight(hight)
        .low(low)
        .volume(volume)
        .last(last)
        .baseVolume(baseVolume)
        .timestamp(timestamp.toString())
        .bid(bid)
        .ask(ask)
        .openBuyOrders(openBuyOrders)
        .openSellOrders(openSellOrders)
        .prevDay(prevDay)
        .created(created.toString())
        .displayMarketName(displayMarketName.toString())
        .build();
  }
}
