/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.exception;

/**
 * The Class EmployeeNotFoundException.
 *
 * @author rodTrejo.
 */
public class EmployeeNotFoundException extends RuntimeException {

  /**
   * Instantiates a new Employee not found exception.
   *
   * @param message the message
   */
  public EmployeeNotFoundException(String message) {
    super(message);
  }

}
