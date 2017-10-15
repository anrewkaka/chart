package xyz.lannt.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import xyz.lannt.application.service.ExchangeService;
import xyz.lannt.constant.ExchangeStatus;
import xyz.lannt.domain.model.CurrencyPrice;
import xyz.lannt.presentation.dto.CurrencyPriceRegistrationDto;

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

  @RequestMapping(value = "/price/{exchange}/{currency}", method = RequestMethod.GET)
  public ResponseEntity<?> getPrice(@PathVariable String exchange, @PathVariable String currency) {
    return ResponseEntity.ok(exchangeService.getPrice(exchange, currency));
  }

  @RequestMapping(value = "/price/{exchange}/{currency}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deletePrice(@PathVariable String exchange, @PathVariable String currency) {
    exchangeService.deletePrice(exchange, currency);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/price/{exchange}", method = RequestMethod.GET)
  public ResponseEntity<?> getPrices(@PathVariable String exchange) {
    return ResponseEntity.ok(exchangeService.getPrices(exchange));
  }

  @RequestMapping(value = "/price/{exchange}", method = RequestMethod.PUT)
  public ResponseEntity<?> setPrice(@PathVariable String exchange, @RequestBody CurrencyPriceRegistrationDto price) {
    exchangeService.setPrice(exchange, CurrencyPrice.fromDto(price));
    return ResponseEntity.ok().build();
  }
//
//  @RequestMapping(value = "/{exchange}/sell", method = RequestMethod.POST)
//  public ResponseEntity<?> sell(@PathVariable String exchange, @RequestBody CurrencySellDto dto) {
//    return ResponseEntity.ok(exchangeService.sell(dto));
//  }
}
