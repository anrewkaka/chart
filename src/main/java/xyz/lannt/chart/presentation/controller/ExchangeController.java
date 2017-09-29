package xyz.lannt.chart.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
  public ModelAndView get(@PathVariable String stockId, @PathVariable String productId) {
    ModelMap model = new ModelMap();
    model.addAttribute("exchange", exchangeService.get(stockId, productId));
    return new ModelAndView("html/exchange", model);
  }
}
