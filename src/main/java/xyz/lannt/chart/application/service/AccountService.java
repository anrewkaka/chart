package xyz.lannt.chart.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.lannt.chart.application.client.MarketClient;
import xyz.lannt.chart.application.client.response.MarketResponse;

@Service
public class AccountService {

  @Autowired
  private MarketClient marketClient;

  public MarketResponse getBalances() {
    return marketClient.getBalance(null);
  }
}
