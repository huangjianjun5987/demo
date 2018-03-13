package com.yatang.sc.provider.web;

import com.busi.common.resp.Response;
import com.google.common.collect.Lists;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return returnType.getMethod().getReturnType().equals(Response.class);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType, Class selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		Response bodyResponse = (Response) body;

		HttpMessage.HttpMessageBuilder builder = HttpMessage.builder();

		builder.timestamp(new Date()).isSuccess(bodyResponse.isSuccess())
				.code(Integer.parseInt(bodyResponse.getCode()))
				.message(bodyResponse.getErrorMessage());

		boolean isNullArray = isNullArray(returnType, bodyResponse);

		builder.data(isNullArray ? Collections.emptyList() : bodyResponse
				.getResultObject());
		return builder.build();
	}

	private boolean isNullArray(MethodParameter returnType,
			Response bodyResponse) {
		Type returnGenericType = returnType.getGenericParameterType();
		if (!(returnGenericType instanceof ParameterizedType)) {
			return false;
		}

		Type resultObjectType = ((ParameterizedType) returnType
				.getGenericParameterType()).getActualTypeArguments()[0];
		Class resultObjectClass = null;
		if (resultObjectType instanceof Class) {
			resultObjectClass = (Class) resultObjectType;
		} else if (resultObjectType instanceof ParameterizedType) {
			resultObjectClass = (Class) ((ParameterizedType) resultObjectType)
					.getRawType();
		}

		if (resultObjectClass == null) {
			return false;
		}

		return (Lists.newArrayList(List.class, Set.class).contains(
				resultObjectClass) || resultObjectClass.isArray())
				&& bodyResponse.getResultObject() == null;

	}

}
