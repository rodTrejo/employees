/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class ErrorConstants.
 *
 * @author rodTrejo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsError {

  public static final String ERROR_TYPE_FORMAT = "exception.%s.type";
  public static final String ERROR_CODE_FORMAT = "exception.%s.code";
  public static final String ERROR_MESSAGE_FORMAT = "exception.%s.message";
  public static final String MESSAGE_NOT_FOUND = "Undefined message key.";
  public static final String COD_INTERNAL_SERVER_ERROR = "500";
  public static final String TYPE_NOT_FOUND = "unmapped-error";
  public static final String EMPLOYEE_ID_IS_REQUIRED =
      "The employee ID is required and cannot be null or blank.";
  public static final String EMPLOYEE_NOT_FOUND = "Employee with the provided ID not found.";
  public static final String AGE_BETWEEN_YEARS_OLD = "Age must be between {0} and {1} years old";
  public static final String EMPLOYEE_DATA_VALIDATION_FAILED = "Employee data validation failed";
  public static final String MONGO_EXCEPTION = "MongoException";

}
