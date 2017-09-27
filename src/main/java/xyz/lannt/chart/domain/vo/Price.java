package xyz.lannt.chart.domain.vo;

import java.math.BigDecimal;

import org.springframework.util.ObjectUtils;

public class Price extends BigDecimal {

  private static final long serialVersionUID = 2656902385439860755L;

  public Price(Float val) {
    super(ObjectUtils.isEmpty(val) ? 0 : val);
  }

  public static Price create(Float value) {
    return new Price(value);
  }
}
