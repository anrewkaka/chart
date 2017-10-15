package xyz.lannt.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.lannt.domain.vo.CryptoValue;

@Data
@Builder
@AllArgsConstructor
public class BalanceProfitDto {
  private String name;

  private CryptoValue amount;

  private CryptoValue buyPrice;

  private CryptoValue currentPrice;

  private CryptoValue profit;

  private CryptoValue profitInPercentage;
}
