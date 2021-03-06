package xyz.lannt.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import xyz.lannt.application.property.BittrexMarketProperty;
import xyz.lannt.application.service.AccountService;
import xyz.lannt.presentation.dto.ExchangeProfitDto;

@Controller
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private BittrexMarketProperty property;
  
  @Autowired
  private Gson gson;
  
  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/{exchange}/profit", method = RequestMethod.GET)
  public String getProfit(@PathVariable String exchange, Model model) {
    model.addAttribute("exchange", exchange);
    return "html/account/profit";
  }

  @MessageMapping("/account_profit")
  @SendTo("/topic/account_profit")
  public ResponseEntity<?> getProfit(ExchangeProfitDto dto) throws Exception {
    return ResponseEntity.ok(gson.toJson(accountService.getProfit(dto.getExchange(), dto.getBaseCurrency())));
  }

//  @MessageMapping("/sell")
//  @SendTo("/topic/sell")
//  public ResponseEntity<?> sell(ExchangeSellDto dto) throws Exception {
//    Market market = Market.of(dto.getExchange());
//    MarketRequest request = MarketRequestSellingFactory.initial(market).create(property.getApiKey(), dto);
//    return ResponseEntity.ok(accountService.);
//  }
}
