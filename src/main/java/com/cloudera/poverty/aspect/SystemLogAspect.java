package com.cloudera.poverty.aspect;

import com.cloudera.poverty.annotation.FmtType;
import com.cloudera.poverty.annotation.SystemLog;
import com.cloudera.poverty.common.utils.IpUtils;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.common.utils.SystemLogFmtUtils;
import com.google.common.primitives.Longs;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.*;

@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    ISystemLogCustomLogic customLogic;

    @Pointcut("@annotation(com.cloudera.poverty.annotation.SystemLog)")
    public void annotationPointcut() {

    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    	// 此处进入到方法前  可以实现一些业务逻辑
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        String[] params = methodSignature.getParameterNames();// 获取参数名称
        Object[] args = joinPoint.getArgs();// 获取参数值
        FmtType fmtType = methodSignature.getMethod().getAnnotation(SystemLog.class).formatType();//格式化类型
        String desc = methodSignature.getMethod().getAnnotation(SystemLog.class).description(); //描述
        String[] sys = methodSignature.getMethod().getAnnotation(SystemLog.class).sysParams(); //需要序列化的对象
        Class bean = methodSignature.getMethod().getAnnotation(SystemLog.class).beanType(); //需要使用bean的类

        //参数
//        List arguments = new ArrayList();
//        for (Object obj : args) {
//            if ((obj instanceof HttpServletRequest) || (obj instanceof HttpServletResponse) || obj instanceof BindingResult) {
//                continue;
//            }
//            arguments.add(obj);
//        }

        StringBuilder sb = new StringBuilder();
        if(claims.get("dname") != null){
            sb.append(claims.get("dname"));
            if(claims.get("tname") != null){
                sb.append(",").append(claims.get("tname"));
                if(claims.get("rname") != null){
                    sb.append(",").append(claims.get("rname"));
                }
            }
        }

        Map prop = new HashMap<String, Object>();
        prop.put("paramName", Arrays.toString(params));
        prop.put("paramVal", Arrays.toString(args));
        prop.put("method", methodSignature.getName());
        prop.put("desc", SystemLogFmtUtils.getValue(fmtType, desc, bean, params, args, sys));
        prop.put("username", claims.get("showname"));
        prop.put("params", sb.toString());
        prop.put("uid", claims.get("uid"));
        prop.put("ip", IpUtils.getIpAddrByRequest(request));
        this.customLogic.execute(prop);
        return joinPoint.proceed();
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     * @param joinPoint
     */
    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }
}