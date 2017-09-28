package xyz.lannt.chart.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.lannt.chart.application.service.ExchangeService;
import xyz.lannt.chart.constant.ExchangeStatus;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

  @Autowired
  private ExchangeService exchangeService;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> get(ExchangeStatus status) {
    return ResponseEntity.ok(exchangeService.get(status));
  }

  @RequestMapping(value = "/{stockId}/{productId}", method = RequestMethod.GET)
  public String get(@PathVariable String stockId, @PathVariable String productId, Model model) {
    model.addAttribute("exchange", "exchangeService.get(stockId, productId)");
    return "html/exchange";
  }
}
