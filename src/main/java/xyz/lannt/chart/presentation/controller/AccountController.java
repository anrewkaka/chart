package xyz.lannt.chart.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import xyz.lannt.application.client.MarketClientFactory;
import xyz.lannt.chart.application.property.BittrexMarketProperty;
import xyz.lannt.chart.presentation.dto.ExchangeProfitDto;
import xyz.lannt.chart.presentation.dto.ExchangeSellDto;
import xyz.lannt.constant.Market;

@Controller
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private MarketClientFactory marketClientFactory;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private BittrexMarketProperty property;

  @RequestMapping(value = "/{exchange}/profit", method = RequestMethod.GET)
  public String getProfit(@PathVariable String exchange, Model model) {
    model.addAttribute("exchange", exchange);
    return "html/account/profit";
  }

  @MessageMapping("/account_profit")
  @SendTo("/topic/account_profit")
  public ResponseEntity<?> getProfit(ExchangeProfitDto dto) throws Exception {
    // restTemplate.getForObject(property.getUrl() + "/account/profit/" + baseCurrency, String.class)
    return ResponseEntity.ok(marketClientFactory.getClient(Market.of(dto.getExchange())).getBalances());
  }

  @MessageMapping("/sell")
  @SendTo("/topic/sell")
  public ResponseEntity<?> sell(ExchangeSellDto dto) throws Exception {
    // restTemplate.postForObject(property.getUrl() + "/exchange/bittrex/sell/", dto, String.class)
    return ResponseEntity.ok().build();
  }
}
