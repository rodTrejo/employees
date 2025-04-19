/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.service;

import java.util.List;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;

/**
 * The interface Employee service.
 */
public interface EmployeeService {

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  List<EmployeeResponseDto> getAllEmployees();

  /**
   * Delete employee.
   *
   * @param idEmployee the id employee
   */
  void deleteEmployee(String idEmployee);

  /**
   * Update employee employee response dto.
   *
   * @param idEmployee the id employee
   * @param employee   the employee
   * @return the employee response dto
   */
  EmployeeResponseDto updateEmployee(String idEmployee, EmployeeRequestDto employee);

  /**
   * Insert employees list.
   *
   * @param employees the employees
   * @return the list
   */
  EmployeeInsertResponseDto insertEmployees(List<EmployeeRequestDto> employees);

}
