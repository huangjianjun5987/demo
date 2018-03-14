package com.yatang.sc.web;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

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
