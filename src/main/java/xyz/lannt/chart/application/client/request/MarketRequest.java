package xyz.lannt.chart.application.client.request;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyz.lannt.chart.application.client.annotation.MarketQueryParam;
import xyz.lannt.chart.application.exception.ChartException;

public interface MarketRequest {

  default String toQueryParam() throws IllegalAccessException {
    return getFields(MarketRequest.class).stream()
      .map(e -> e.getAnnotation(MarketQueryParam.class).value() + "=" + getFieldValue(e))
      .reduce((v1, v2) -> String.join("&", v1, v2))
      .orElse("");
  }

  default List<Field> getFields(Class<?> clazz) {
    Class<?> superClass = clazz.getSuperclass();
    if (superClass == null) {
      return Collections.emptyList();
    }

    List<Field> fields = new ArrayList<Field>();
    for (Field field : clazz.getDeclaredFields()) {
      fields.add(field);

    }

    fields.addAll(getFields(superClass));

    return fields;
  }

  default String getFieldValue(Field field) {

    field.setAccessible(true);
    Class<?> targetType = field.getType();
    Object fieldData;
    try {
      fieldData = field.get(this);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new ChartException(e);
    }
    if (fieldData == null) {
      return null;
    }

    if (targetType.isPrimitive()) {
      return String.valueOf(fieldData);
    }

    return fieldData.toString();
  }
}
