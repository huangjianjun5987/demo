package com.yatang.sc.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.web.jackson.EmptyBeanSerializerModifier;

@Configuration
public class MvcConfig {

  @Value("${image.view.domain}")
  private String imageViewDomain;//图片域名
  @Value("${html.view.url}")
  private String fileViewDomain;//静态文件地址

  @Bean
  public EmptyBeanSerializerModifier emptyBeanSerializerModifier() {
    return new EmptyBeanSerializerModifier();
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(emptyBeanSerializerModifier()));
    objectMapper.setConfig(objectMapper.getSerializationConfig().withAttribute("imageViewDomain", imageViewDomain).withAttribute("fileViewDomain",fileViewDomain));
    objectMapper.setConfig(objectMapper.getDeserializationConfig().withAttribute("imageViewDomain", imageViewDomain).withAttribute("fileViewDomain",fileViewDomain));
    return objectMapper;
  }

}
