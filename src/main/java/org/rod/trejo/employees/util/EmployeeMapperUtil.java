/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.util;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.rod.trejo.employees.constant.Constants;
import org.rod.trejo.employees.model.EmployeeCollection;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto.EmployeeRequestDtoBuilder;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;

/**
 * The Class EmployeeMapperUtil.
 *
 * @author rodTrejo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapperUtil {

  /**
   * Map request to employee employee collection.
   *
   * @param dto the dto
   * @return the employee collection
   */
  public static EmployeeCollection mapRequestToEmployee(EmployeeRequestDto dto) {
    EmployeeCollection employee = new EmployeeCollection();
    mapRequestToEmployee(employee, dto);
    return employee;
  }

  /**
   * Map request to employee.
   *
   * @param employee the employee
   * @param dto      the dto
   */
  public static void mapRequestToEmployee(EmployeeCollection employee, EmployeeRequestDto dto) {
    employee.setFirstName(dto.firstName());
    employee.setMiddleName(dto.middleName());
    employee.setLastName(dto.lastName());
    employee.setSecondLastName(dto.secondLastName());
    employee.setGender(dto.gender());
    employee.setBirthDate(dto.birthDate());
    employee.setPosition(dto.position());
  }

  /**
   * Map to employee response dto employee response dto.
   *
   * @param employeeCollection the employee collection
   * @return the employee response dto
   */
  public static EmployeeResponseDto mapToEmployeeResponseDto(
      EmployeeCollection employeeCollection) {
    return EmployeeResponseDto.builder()
      .idEmployee(employeeCollection.getId())
      .firstName(employeeCollection.getFirstName())
      .middleName(Optional.ofNullable(employeeCollection.getMiddleName())
          .orElse(Constants.EMPTY_STRING))
      .lastName(employeeCollection.getLastName())
      .secondLastName(Optional.ofNullable(employeeCollection.getSecondLastName())
          .orElse(Constants.EMPTY_STRING))
      .gender(employeeCollection.getGender())
      .birthDate(employeeCollection.getBirthDate())
      .position(employeeCollection.getPosition())
      .age(employeeCollection.getAge()).build();
  }

  /**
   * Map employee request dto builder employee request dto builder.
   *
   * @param dto the dto
   * @return the employee request dto builder
   */
  public static EmployeeRequestDtoBuilder mapEmployeeRequestDtoBuilder(EmployeeRequestDto dto) {
    return EmployeeRequestDto.builder()
      .firstName(dto.firstName())
      .middleName(dto.middleName())
      .lastName(dto.lastName())
      .secondLastName(dto.secondLastName())
      .gender(dto.gender())
      .birthDate(dto.birthDate())
      .position(dto.position());
  }
}
