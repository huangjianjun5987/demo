package com.yatang.sc.payment.web.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.util.List;

public class EmptyBeanSerializerModifier extends BeanSerializerModifier {

  private JsonSerializer<Object> _nullArrayJsonSerializer = new NullArrayJsonSerializer();
  private JsonSerializer<Object> _nullBeanJsonSerializer = new NullBeanJsonSerializer();

  @Override
  public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                   List<BeanPropertyWriter> beanProperties) {
    for (BeanPropertyWriter writer : beanProperties) {
      // 判断字段的类型，如果是array，list，set则注册nullSerializer
      if (isArrayType(writer)) {
        //给writer注册一个自己的nullSerializer
        writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
        continue;
      }
      if (isBeanType(writer)) {
        //给writer注册一个自己的nullSerializer
        writer.assignNullSerializer(this.defaultNullBeanJsonSerializer());
        continue;
      }
    }
    return beanProperties;
  }

  protected boolean isArrayType(BeanPropertyWriter writer) {
    JavaType javaType = writer.getType();
    return javaType.isArrayType() || javaType.isCollectionLikeType();

  }

  protected boolean isBeanType(BeanPropertyWriter writer) {
    JavaType javaType = writer.getType();
    return javaType.isJavaLangObject() || javaType.isMapLikeType();

  }

  protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {
    return _nullArrayJsonSerializer;
  }

  protected JsonSerializer<Object> defaultNullBeanJsonSerializer() {
    return _nullBeanJsonSerializer;
  }

  public class NullArrayJsonSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
      if (value == null) {
        jgen.writeStartArray();
        jgen.writeEndArray();
      } else {
        jgen.writeObject(value);
      }
    }
  }

  public class NullBeanJsonSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
      if (value == null) {
        jgen.writeStartObject();
        jgen.writeEndObject();
      } else {
        jgen.writeObject(value);
      }
    }
  }

}