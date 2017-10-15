package xyz.lannt.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.lannt.domain.vo.CryptoValue;

@Data
@Builder
@AllArgsConstructor
public class BalanceDto {

  private String currency;

  private CryptoValue balance;

  private CryptoValue available;

  private CryptoValue pending;

  private String address;

}
