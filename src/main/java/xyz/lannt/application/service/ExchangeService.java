package xyz.lannt.application.service;

import static xyz.lannt.constant.ExchangeStatus.OPEN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.lannt.constant.ExchangeStatus;
import xyz.lannt.domain.model.CurrencyPrice;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.infrastructure.repository.ExchangeRepository;
import xyz.lannt.presentation.dto.CurrencyPriceDetailDto;
import xyz.lannt.presentation.dto.ExchangeDto;

@Service
public class ExchangeService {

  @Autowired
  private ExchangeRepository exchangeRepository;

  public List<ExchangeDto> get(ExchangeStatus status) {
    return OPEN.equals(status) ? exchangeRepository.findOpen().toDtoes() : exchangeRepository.findClosed().toDtoes();
  }

  public ExchangeDto get(String stockId, String productId) {
    return exchangeRepository.find(stockId, productId).toDto();
  }

  public void setPrice(String exchange, CurrencyPrice price) {
    exchangeRepository.set(exchange, price);
  }

  public List<CurrencyPriceDetailDto> getPrices(String exchange) {
    return exchangeRepository.get(exchange).toDtoes();
  }

  public CryptoValue getPrice(String exchange, String currency) {
    return exchangeRepository.get(exchange, currency).priceInAverage();
  }

  public void deletePrice(String exchange, String currency) {
    exchangeRepository.delete(exchange, currency);
  }
}
