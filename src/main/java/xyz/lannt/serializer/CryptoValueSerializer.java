package xyz.lannt.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import xyz.lannt.domain.vo.CryptoValue;

public class CryptoValueSerializer implements JsonSerializer<CryptoValue> {

  @Override
  public JsonElement serialize(CryptoValue src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}
