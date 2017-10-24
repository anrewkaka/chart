package xyz.lannt.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import xyz.lannt.application.service.ExchangeService;
import xyz.lannt.constant.ExchangeStatus;
import xyz.lannt.domain.model.CurrencyPrice;
import xyz.lannt.presentation.dto.CurrencyPriceDeleteDto;
import xyz.lannt.presentation.dto.CurrencyPriceRegistrationDto;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

  @Autowired
  private ExchangeService exchangeService;

  @Autowired
  private Gson gson;

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

  @RequestMapping(value = "/price/{exchange}", method = RequestMethod.GET)
  public String getPrices(@PathVariable String exchange, Model model) {
    model.addAttribute("exchange", exchange);
    return "html/prices/list";
  }

  @MessageMapping(value = "/price-delete")
  @SendTo("/topic/price")
  public ResponseEntity<?> deletePrice(CurrencyPriceDeleteDto dto) {
    exchangeService.deletePrice(dto.getExchange(), dto.getCurrency());
    return ResponseEntity.ok().build();
  }

  @MessageMapping(value = "/get-list")
  @SendTo("/topic/price")
  public ResponseEntity<?> getPrices(String exchange) {
    return ResponseEntity.ok(gson.toJson(exchangeService.getPrices(exchange)));
  }

  @MessageMapping(value = "/price-registration")
  @SendTo("/topic/price")
  public ResponseEntity<?> setPrice(CurrencyPriceRegistrationDto dto) {
    exchangeService.setPrice(dto.getExchange(), CurrencyPrice.fromDto(dto));
    return ResponseEntity.ok().build();
  }
//
//  @RequestMapping(value = "/{exchange}/sell", method = RequestMethod.POST)
//  public ResponseEntity<?> sell(@PathVariable String exchange, @RequestBody CurrencySellDto dto) {
//    return ResponseEntity.ok(exchangeService.sell(dto));
//  }
}
