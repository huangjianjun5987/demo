package com.yatang.sc.provider.web.paramvalid;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @描述: 参数拦截的切面类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/1 9:41
 * @版本: v1.0
 */
@Slf4j
@Component
@Aspect
public class ParamValidAspect {


    @Pointcut("@annotation(com.yatang.sc.provider.web.paramvalid.ParamValid)")
    public void paramValid() {

    }

    @Before("paramValid()")
    public void before(JoinPoint joinpoint) {
        log.info("请求参数:{}", JSON.toJSONString(joinpoint.getArgs()));
        //取参数，如果没参数，那肯定不校验了
        Object[] objects = joinpoint.getArgs();
        if (objects.length > 0) {
            /**************************校验普通参数*************************/
            //  获得切入目标对象
            Object target = joinpoint.getThis();
            // 获得切入的方法
            Method method = ((MethodSignature) joinpoint.getSignature()).getMethod();
            // 执行校验，获得校验结果
            Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, objects);
            if (!validResult.isEmpty()) {//如果有校验不通过的进行处理
                Map<String, String> map = new HashMap<>();
                String[] parameterNames = parameterNameDiscoverer.getParameterNames(method); // 获得方法的参数名称
                for (ConstraintViolation<Object> constraintViolation : validResult) {
                    PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();  // 获得校验的参数路径信息
                    int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
                    String paramName = parameterNames[paramIndex];  // 获得校验的参数名称
                    //封装成需要的字段
                    log.error(paramName + ":" + constraintViolation.getMessage());
                    map.put(paramName, constraintViolation.getMessage());
                }
                //当获得的map有值的时候抛异常由统一拦截的地方处理
                if (map.size() > 0) {
                    throw new ParamValidException(map);
                }
            }
        }
    }

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }
}
