package com.yatang.sc.payment.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.payment.web.jackson.EmptyBeanSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

    @Bean
    public EmptyBeanSerializerModifier emptyBeanSerializerModifier() {
        return new EmptyBeanSerializerModifier();
    }

    @Bean
    public ObjectMapper emptyObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(emptyBeanSerializerModifier()));
        return objectMapper;
    }

}
