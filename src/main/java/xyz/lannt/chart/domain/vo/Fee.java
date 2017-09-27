package xyz.lannt.chart.domain.vo;

import java.math.BigDecimal;

import org.springframework.util.ObjectUtils;

public class Fee extends BigDecimal {

  private static final long serialVersionUID = -4423678731206587593L;

  public Fee(Float val) {
    super(ObjectUtils.isEmpty(val) ? 0 : val);
  }

  public static Fee create(Float value) {
    return new Fee(value);
  }
}
