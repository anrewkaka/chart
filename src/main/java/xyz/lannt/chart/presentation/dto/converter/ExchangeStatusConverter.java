package xyz.lannt.chart.presentation.dto.converter;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import xyz.lannt.chart.application.exception.ChartException;
import xyz.lannt.chart.constant.ExchangeStatus;

public class ExchangeStatusConverter implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().isAssignableFrom(ExchangeStatus.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return ExchangeStatus.of(webRequest.getParameter("status"), () -> new ChartException());
  }

}
