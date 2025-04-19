/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.exception.advice;

import com.mongodb.MongoException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rod.trejo.employees.constant.ConstantsError;
import org.rod.trejo.employees.exception.EmployeeException;
import org.rod.trejo.employees.exception.EmployeeNotFoundException;
import org.rod.trejo.employees.exception.EmployeeValidationException;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.ErrorResponseDto;
import org.rod.trejo.employees.model.dto.ErrorResponseDto.ErrorResponseDtoBuilder;
import org.springframework.context.MessageSource;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * The Class EmployeeControllerAdvice.
 *
 * @author rodTrejo.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class EmployeeControllerAdvice {

  /**
   * The Message source.
   */
  private final MessageSource messageSource;

  /**
   * Handle bad request exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleBadRequestException(NoResourceFoundException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle method not allowed exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(MethodNotAllowedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ErrorResponseDto handleMethodNotAllowedException(MethodNotAllowedException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle decoding exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(DecodingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleDecodingException(DecodingException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle unsupported media type status exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public ErrorResponseDto handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle employee validation exception employee insert response dto.
   *
   * @param exception the exception
   * @return the employee insert response dto
   */
  @ExceptionHandler(EmployeeValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public EmployeeInsertResponseDto handleEmployeeValidationException(
      EmployeeValidationException exception) {
    return exception.getEmployeeInsertResponseDto();
  }

  /**
   * Handle validation exceptions error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    String errors =
        exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
    return getErrorData(exception.getClass().getSimpleName()).detail(errors).build();
  }

  /**
   * Handle employee exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(EmployeeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleEmployeeException(EmployeeException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle employee not found exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(EmployeeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
    return getErrorData(exception.getClass().getSimpleName()).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Handle mongo exception error response dto.
   *
   * @param exception the exception
   * @return the error response dto
   */
  @ExceptionHandler(MongoException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDto handleRodMongoException(MongoException exception) {
    return getErrorData(ConstantsError.MONGO_EXCEPTION).detail(
        exception.getLocalizedMessage()).build();
  }

  /**
   * Gets error data.
   *
   * @param exceptionName the exception name
   * @return the error data
   */
  private ErrorResponseDtoBuilder getErrorData(String exceptionName) {
    return ErrorResponseDto.builder().code(
            messageSource.getMessage(String.format(ConstantsError.ERROR_CODE_FORMAT, exceptionName),
                null, ConstantsError.COD_INTERNAL_SERVER_ERROR, Locale.getDefault())).type(
            messageSource.getMessage(String.format(ConstantsError.ERROR_TYPE_FORMAT, exceptionName),
                null, ConstantsError.TYPE_NOT_FOUND, Locale.getDefault())).message(
            messageSource.getMessage(String.format(ConstantsError.ERROR_MESSAGE_FORMAT,
                    exceptionName),
                null, ConstantsError.MESSAGE_NOT_FOUND, Locale.getDefault()))
        .timestamp(LocalDateTime.now());
  }

}
