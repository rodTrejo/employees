/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class Constantes.
 *
 * @author rodTrejo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  public static final String ERROR = "ERROR";
  public static final String API_BASE_PATH = "api.base-path";
  public static final String API_PATH = "/api/v1/employees";
  public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
  public static final String BETWEENYEARS_OLD = "Age must be between {0} and {1} years old";

  public static final String MSG_BETWEEN_YEARS_OLD = "Age must be between 18 and 65 years old";
}
