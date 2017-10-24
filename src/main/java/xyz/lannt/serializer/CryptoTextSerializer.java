package xyz.lannt.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import xyz.lannt.domain.vo.CryptoText;


public class CryptoTextSerializer implements JsonSerializer<CryptoText> {

  @Override
  public JsonElement serialize(CryptoText src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}
