/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class ConstantsLogs.
 *
 * @author rodTrejo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsLog {

  public static final String EMPLOYEE_SUCCESSFULLY_DELETED =
      "Employee successfully deleted (deactivated): {}";
  public static final String EMPLOYEE_SUCCESSFULLY_UPDATED = "Employee successfully updated: {}";
  public static final String VALIDATION_ERROR_FOR_EMPLOYEE = "Validation error for employee {}: {}";
  public static final String EMPLOYEE_SAVED_SUCCESSFULLY = "Employee saved successfully: {}";
  public static final String EXCEPTION_SAVING_EMPLOYEE =
      "Error saving employee: {}; Exception message: {}";
  public static final String METHOD_EXECUTION_STARTED =
      "Method execution started - {}.{} | Args: {}";
  public static final String METHOD_EXECUTION_COMPLETED =
      "Method execution completed - {}.{} | Execution time: {} ms";
  public static final String REQUEST_HEADER = "Request Header: {} = {}";
}
