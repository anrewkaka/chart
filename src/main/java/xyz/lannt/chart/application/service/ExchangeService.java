package xyz.lannt.chart.application.service;

import static xyz.lannt.chart.constant.ExchangeStatus.OPEN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.lannt.chart.constant.ExchangeStatus;
import xyz.lannt.chart.infrastructure.repository.ExchangeRepository;
import xyz.lannt.chart.presentation.dto.ExchangeDto;

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
}
