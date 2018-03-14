package com.yatang.sc.app.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.app.web.jackson.EmptyBeanSerializerModifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

  @Value("${image.view.url}")
  private String		fileServerUrl;

  @Bean
  public EmptyBeanSerializerModifier emptyBeanSerializerModifier() {
    return new EmptyBeanSerializerModifier();
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(emptyBeanSerializerModifier()));
    objectMapper.setConfig(objectMapper.getSerializationConfig().withAttribute("imageViewDomain", fileServerUrl));
    objectMapper.setConfig(objectMapper.getDeserializationConfig().withAttribute("imageViewDomain", fileServerUrl));
    return objectMapper;
  }

}