/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.util;

import java.time.LocalDate;
import org.rod.trejo.employees.model.EmployeeCollection;
import org.rod.trejo.employees.model.GenderEnum;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;

/**
 * The Class DtoUtil.
 *
 * @author rodTrejo.
 */
public class DtoUtil {
  public static EmployeeResponseDto createEmployeeResponseDto(String idEmpleado) {
    return EmployeeResponseDto.builder()
        .idEmployee(idEmpleado)
        .firstName("John")
        .middleName("David")
        .lastName("Doe")
        .secondLastName("Smith")
        .gender(GenderEnum.MALE)
        .birthDate(LocalDate.of(1990, 5, 20))
        .position("Software Engineer")
        .age(31)
        .build();
  }
  public static EmployeeRequestDto  createEmployeeRequestDto() {
    return EmployeeRequestDto.builder()
        .firstName("John")
        .middleName("A.")
        .lastName("Doe")
        .secondLastName("Smith")
        .gender(GenderEnum.MALE)
        .birthDate(LocalDate.of(1990, 5, 15))
        .position("Software Engineer").build();
  }

  public static EmployeeCollection createEmployeeCollection() {
    EmployeeCollection employee = new EmployeeCollection();
    employee.setId("1");
    employee.setFirstName("Rodrigo");
    employee.setLastName("Trejo");
    employee.setMiddleName("Noé");
    employee.setSecondLastName("Guerra");
    employee.setGender(GenderEnum.MALE);
    employee.setBirthDate(LocalDate.of(1990, 5, 1));
    employee.setPosition("Developer");
    employee.setActive(true);
    return employee;
  }
}
