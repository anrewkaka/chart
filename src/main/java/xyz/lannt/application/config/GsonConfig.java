package xyz.lannt.application.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.lannt.domain.vo.CryptoText;
import xyz.lannt.domain.vo.CryptoValue;
import xyz.lannt.serializer.CryptoTextSerializer;
import xyz.lannt.serializer.CryptoValueSerializer;

@Configuration
@ConditionalOnClass(Gson.class)
public class GsonConfig extends GsonAutoConfiguration {

  @ConditionalOnMissingBean
  @Override
  public Gson gson() {
    return new GsonBuilder()
        .registerTypeAdapter(CryptoValue.class, new CryptoValueSerializer())
        .registerTypeAdapter(CryptoText.class, new CryptoTextSerializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
  }
}
