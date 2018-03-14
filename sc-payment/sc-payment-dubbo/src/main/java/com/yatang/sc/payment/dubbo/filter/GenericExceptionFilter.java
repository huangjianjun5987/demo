package com.yatang.sc.payment.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.sc.payment.exception.BuzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwei on 2017/7/17.
 * <p>
 */
@Activate(group = {Constants.PROVIDER}, order = 9000)
public class GenericExceptionFilter implements Filter {
    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);

            if (result.hasException()) {
                Throwable exception = result.getException();
                // 是Dubbo本身的异常，直接抛出
                if (exception instanceof RpcException) {
                    return result;
                }
                if (exception instanceof UndeclaredThrowableException) {
                    exception = ((UndeclaredThrowableException) exception).getUndeclaredThrowable();
                }
                Response<String> response;
                if (exception instanceof BuzException) {
                    BuzException buzException = (BuzException) exception;
                    response = new Response<>();
                    response.setSuccess(false);
                    response.setCode(buzException.getCode());
                    response.setErrorMessage(buzException.getMessage());
                } else {
                    mLogger.error("Dubbo 服务异常：{}", JSON.toJSONString(result));
                    response = ResponseUtils.RESPONSE_500();
                }
                RpcResult rpcResult = new RpcResult();
                rpcResult.setValue(response);
                return rpcResult;
            } else {
                return result;
            }
        } catch (Throwable e) {
            RpcResult rpcResult = new RpcResult();
            if (e.getCause() instanceof ConstraintViolationException) {
                Response response = ResponseUtils.RESPONSE_400();
                ConstraintViolationException exception = (ConstraintViolationException) e.getCause();
                Set<ConstraintViolation<?>> validations = exception.getConstraintViolations();
                Map<String, String> validationMap = new HashMap<>(validations.size());
                for (ConstraintViolation<?> validation : validations) {
                    validationMap.put(validation.getPropertyPath().toString(), validation.getMessage());
                }
                response.setErrorMessage(JSON.toJSONString(validationMap));
                rpcResult.setValue(response);
                return rpcResult;
            }

            mLogger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }
}
