/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.exception;

import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;

/**
 * The Class EmployeeValidationException.
 *
 * @author rodTrejo.
 */
public class EmployeeValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The Employee insert response dto.
   */
  private final EmployeeInsertResponseDto employeeInsertResponseDto;


  /**
   * Instantiates a new Employee validation exception.
   *
   * @param message                   the message
   * @param employeeInsertResponseDto the employee insert response dto
   */
  public EmployeeValidationException(String message,
      EmployeeInsertResponseDto employeeInsertResponseDto) {
    super(message);
    this.employeeInsertResponseDto = employeeInsertResponseDto;
  }

  /**
   * Gets employee insert response dto.
   *
   * @return the employee insert response dto
   */
  public EmployeeInsertResponseDto getEmployeeInsertResponseDto() {
    return employeeInsertResponseDto;
  }
}
