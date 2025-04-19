/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class ConstantsEmployeeApi.
 *
 * @author rodTrejo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsEmployeeApi {

  public static final String TAG_EMPLOYEES = "Employees";
  public static final String TAG_EMPLOYEES_DESC = "Operations related to employees";

  public static final String OPERATION_SUMMARY_GET_ALL = "Get all employees";
  public static final String OPERATION_DESC_GET_ALL = "Returns all available employees";
  public static final String DESC_GET_ALL_COD_200 = "Employees retrieved successfully";

  public static final String OPERATION_SUMMARY_INSERT = "Insert employees";
  public static final String OPERATION_DESC_INSERT = "Inserts a list of employees";
  public static final String DESC_INSERT_200 = "Employees inserted successfully";

  public static final String OPERATION_SUMMARY_DELETE = "Delete employee";
  public static final String OPERATION_DESC_DELETE = "Marks the employee as inactive";
  public static final String DESC_DELETE_204 = "Successfully deleted";

  public static final String OPERATION_SUMMARY_UPDATE = "Update employee";
  public static final String OPERATION_DESC_UPDATE = "Updates an existing employee's data";
  public static final String DESC_UPDATE_200 = "Employee updated";

  public static final String OPTIONAL_ERROR_MESSAGE = "Optional error message";

  public static final String COD_200 = "200";
  public static final String COD_201 = "201";
  public static final String COD_204 = "204";
}
