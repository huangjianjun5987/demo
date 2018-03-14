package com.yatang.sc.app.web;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HttpMessage<T> {

  private final Date timestamp;
  private final boolean isSuccess;
  private final int code;
  private final T message;
  private final T data;

}
