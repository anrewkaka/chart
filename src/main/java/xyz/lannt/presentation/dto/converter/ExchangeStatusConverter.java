package xyz.lannt.presentation.dto.converter;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import xyz.lannt.constant.ExchangeStatus;
import xyz.lannt.exception.ChartException;

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
