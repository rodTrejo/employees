/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.rod.trejo.employees.constant.ConstantsEmployeeApi;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;
import org.rod.trejo.employees.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class EmployeeController.
 *
 * @author rodTrejo.
 */
@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path}")
@Tag(name = ConstantsEmployeeApi.TAG_EMPLOYEES,
    description = ConstantsEmployeeApi.TAG_EMPLOYEES_DESC)
public class EmployeeController {

  /**
   * The Employee service.
   */
  private final EmployeeService employeeService;

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  @Operation(
      summary = ConstantsEmployeeApi.OPERATION_SUMMARY_GET_ALL,
      description = ConstantsEmployeeApi.OPERATION_DESC_GET_ALL,
      responses = {
          @ApiResponse(responseCode = ConstantsEmployeeApi.COD_200,
              description = ConstantsEmployeeApi.DESC_GET_ALL_COD_200,
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  array = @ArraySchema(
                      schema = @Schema(implementation = EmployeeResponseDto.class)
                  )
              ))
      }
  )
  @GetMapping
  public List<EmployeeResponseDto> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  /**
   * Insert employees flux.
   *
   * @param employees the employees
   * @return the flux
   */
  @Operation(
      summary = ConstantsEmployeeApi.OPERATION_SUMMARY_INSERT,
      description = ConstantsEmployeeApi.OPERATION_DESC_INSERT,
      responses = {
          @ApiResponse(responseCode = ConstantsEmployeeApi.COD_201,
              description = ConstantsEmployeeApi.DESC_INSERT_200,
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = EmployeeInsertResponseDto.class)
              )
          ))
      }
  )
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public EmployeeInsertResponseDto insertEmployees(
      @RequestBody List<EmployeeRequestDto> employees) {
    return employeeService.insertEmployees(employees);
  }

  /**
   * Delete employee mono.
   *
   * @param idEmployee the id employee
   */
  @Operation(
      summary = ConstantsEmployeeApi.OPERATION_SUMMARY_DELETE,
      description = ConstantsEmployeeApi.OPERATION_DESC_DELETE,
      responses = {
          @ApiResponse(responseCode = ConstantsEmployeeApi.COD_204,
              description = ConstantsEmployeeApi.DESC_DELETE_204)
      }
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{idEmployee}")
  public void deleteEmployee(@PathVariable String idEmployee) {
    employeeService.deleteEmployee(idEmployee);
  }

  /**
   * Update employee mono.
   *
   * @param idEmployee the id employee
   * @param employee   the employee
   * @return the mono
   */
  @Operation(
      summary = ConstantsEmployeeApi.OPERATION_SUMMARY_UPDATE,
      description = ConstantsEmployeeApi.OPERATION_DESC_UPDATE,
      responses = {
          @ApiResponse(responseCode = ConstantsEmployeeApi.COD_200,
              description = ConstantsEmployeeApi.DESC_UPDATE_200,
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = EmployeeResponseDto.class)))
      }
  )
  @PutMapping("/{idEmployee}")
  public EmployeeResponseDto updateEmployee(@PathVariable String idEmployee,
      @RequestBody EmployeeRequestDto employee) {
    return employeeService.updateEmployee(idEmployee, employee);
  }
}
