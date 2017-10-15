package xyz.lannt.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.CryptoValue;

@Data
@Builder
@AllArgsConstructor
public class CurrencyPriceDetailDto {

  private CryptoText name;
  
  private CryptoValue price;
}
