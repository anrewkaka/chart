package xyz.lannt.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.lannt.infrastructure.dao.TestExchangeDao;

@Configuration
public class ExchangeDaoConfig {

  @Autowired
  private TestExchangeDao testExchangeDao;

  @Bean
  public TestExchangeDao exchangeDao() {
    return testExchangeDao;
  }
}
