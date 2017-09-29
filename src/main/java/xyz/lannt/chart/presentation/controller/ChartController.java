package xyz.lannt.chart.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChartController {

  @RequestMapping("/chart")
  public String get() {
    return "chart";
  }
}
