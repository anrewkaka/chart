package xyz.lannt.domain.vo;

import java.math.BigDecimal;

import org.springframework.util.ObjectUtils;

public class Quantity extends BigDecimal {

  private static final long serialVersionUID = -2788365334805173203L;

  public Quantity(Double val) {
    super(ObjectUtils.isEmpty(val) ? 0 : val);
  }

  public static Quantity create(Double value) {
    return new Quantity(value);
  }
}
