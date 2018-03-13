package com.yatang.sc.provider.web;

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
  private final Exception exception;
  private final T data;




}
