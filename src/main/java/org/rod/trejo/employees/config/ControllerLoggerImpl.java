/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.rod.trejo.employees.constant.ConstantsLog;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The Class ControllerLoggerImpl.
 *
 * @author rodTrejo.
 */
@Aspect
@Component
@Slf4j
public class ControllerLoggerImpl {

  /**
   * Log data.
   *
   * @param joinPoint the join point.
   * @return the object.
   * @throws Throwable the throwable.
   */
  @Around("execution(* org.rod.trejo.employees.controller.*.*(..))")
  public Object logData(ProceedingJoinPoint joinPoint) throws Throwable {

    HttpServletRequest request =
        ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
    StopWatch sw = new StopWatch();
    sw.start();

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String methodName = methodSignature.getMethod().getName();

    logRequestHeaders(request);

    log.info(ConstantsLog.METHOD_EXECUTION_STARTED,
        joinPoint.getTarget().getClass().getSimpleName(), methodName,
        Arrays.toString(joinPoint.getArgs()));


    Object result = joinPoint.proceed();
    sw.stop();

    log.info(ConstantsLog.METHOD_EXECUTION_COMPLETED,
        joinPoint.getTarget().getClass().getSimpleName(), methodName, sw.getTotalTimeMillis());

    return result;
  }

  private void logRequestHeaders(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        String headerValue = request.getHeader(headerName);
        log.info(ConstantsLog.REQUEST_HEADER, headerName, headerValue);
      }
    }
  }
}
