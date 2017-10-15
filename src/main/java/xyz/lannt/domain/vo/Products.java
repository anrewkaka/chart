package xyz.lannt.domain.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Products extends ArrayList<String> {

  private static final long serialVersionUID = 2727352985001875621L;

  public Products() {
    super();
  }

  public Products(String[] markets) {
    super();
    addAll(Arrays.asList(markets));
  }

  public Products(List<String> markets) {
    super();
    addAll(markets);
  }
}
