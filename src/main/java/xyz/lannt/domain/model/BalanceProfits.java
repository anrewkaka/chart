package xyz.lannt.domain.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import xyz.lannt.presentation.dto.BalanceProfitDto;

@AllArgsConstructor
public class BalanceProfits {

  private List<BalanceProfit> values;

  public BalanceProfits() {
    values = new ArrayList<>();
  }

  public List<BalanceProfitDto> toDtoes() {
    return values.stream()
        .map(BalanceProfit::toDto)
        .collect(toList());
  }
}
