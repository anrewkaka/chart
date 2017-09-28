package xyz.lannt.chart.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/market")
public class MarketController {

  @RequestMapping(method = RequestMethod.GET)
  public String get() {
    return "html/market";
  }
}
