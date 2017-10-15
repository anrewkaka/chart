package xyz.lannt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import xyz.lannt.presentation.dto.MarketCompareDto;

@Builder
@AllArgsConstructor
public class MarketComapre {

  private static double BITTREX_FEE = 0.0005;

  private MarketSummary base;

  private MarketSummary source;

  private MarketSummary target;

  public MarketCompareDto calculate() {
    return MarketCompareDto.builder()
        .base(base.toDto())
        .source(source.toDto())
        .target(target.toDto())
        .last(base.getLast().multiply(source.getLast()).subtract(target.getLast()))
        .bid(base.getBid().multiply(source.getBid()).subtract(target.getBid()))
        .ask(base.getAsk().multiply(source.getAsk()).subtract(target.getAsk()))
        .build();
  }
}
