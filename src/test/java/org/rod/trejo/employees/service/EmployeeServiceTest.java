/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.service;


import com.mongodb.MongoException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rod.trejo.employees.config.EmployeeProperties;
import org.rod.trejo.employees.constant.ConstantsError;
import org.rod.trejo.employees.exception.EmployeeException;
import org.rod.trejo.employees.exception.EmployeeNotFoundException;
import org.rod.trejo.employees.exception.EmployeeValidationException;
import org.rod.trejo.employees.model.EmployeeCollection;
import org.rod.trejo.employees.model.GenderEnum;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;
import org.rod.trejo.employees.repository.EmployeeRepository;
import org.rod.trejo.employees.service.impl.EmployeeServiceImpl;
import org.rod.trejo.employees.util.Constants;
import org.rod.trejo.employees.util.DtoUtil;
import org.rod.trejo.employees.util.EmployeeMapperUtil;

/**
 * The Class EmployeeServiceTest.
 *
 * @author rodTrejo.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  /**
   * The Employee service.
   */
  @InjectMocks
  private EmployeeServiceImpl employeeService;

  /**
   * The Employee repository.
   */
  @Mock
  private EmployeeRepository employeeRepository;

  /**
   * The Validator.
   */
  @Mock
  private Validator validator;

  /**
   * The Employee properties.
   */
  @Mock
  private  EmployeeProperties employeeProperties;

  /**
   * The Employee id.
   */
  private String employeeId;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    Mockito.lenient().when(employeeProperties.getMinimumAge()).thenReturn(18);
    Mockito.lenient().when(employeeProperties.getMaximumAge()).thenReturn(65);
    Mockito.lenient().when(employeeProperties.getMessageErrorAge()).thenReturn(
        Constants.BETWEENYEARS_OLD);
    Mockito.lenient().when(employeeProperties.getMessageErrorAgeValid()).thenReturn(
        Constants.MSG_BETWEEN_YEARS_OLD);

    employeeId = UUID.randomUUID().toString();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  /**
   * Gets all employees return list.
   */
  @Test
  void getAllEmployeesReturnList() {
    Mockito.when(employeeRepository.findAllByActiveTrue()).thenReturn(List.of(
        DtoUtil.createEmployeeCollection()));
    List<EmployeeResponseDto> result = employeeService.getAllEmployees();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());
    Mockito.verify(employeeRepository).findAllByActiveTrue();
  }

  /**
   * Delete employee successfully.
   */
  @Test
  void deleteEmployeeSuccessfully() {
    EmployeeCollection expectedEmployeeToSave = DtoUtil.createEmployeeCollection();
    expectedEmployeeToSave.setActive(false);
    Mockito.when(employeeRepository.findByIdAndActiveTrue(employeeId))
        .thenReturn(Optional.of(DtoUtil.createEmployeeCollection()));
    Mockito.when(employeeRepository.save(Mockito.any(EmployeeCollection.class)))
        .thenAnswer(i -> i.getArgument(0));

    employeeService.deleteEmployee(employeeId);
    Mockito.verify(employeeRepository).save(expectedEmployeeToSave);
    Mockito.verify(employeeRepository).findByIdAndActiveTrue(employeeId);
  }

  /**
   * Delete employee does not exist.
   */
  @Test
  void deleteEmployeeDoesNotExist() {
    Mockito.when(employeeRepository.findByIdAndActiveTrue(employeeId)).thenReturn(Optional.empty());

    EmployeeNotFoundException exception = Assertions.assertThrows(
        EmployeeNotFoundException.class,
        () -> employeeService.deleteEmployee(employeeId)
    );

    Assertions.assertEquals(ConstantsError.EMPLOYEE_NOT_FOUND, exception.getMessage());

    Mockito.verify(employeeRepository).findByIdAndActiveTrue(employeeId);
    Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any());
  }

  /**
   * Update employee successfully.
   */
  @Test
  void updateEmployeeSuccessfully() {
    EmployeeRequestDto request = DtoUtil.createEmployeeRequestDto();
    EmployeeCollection existingEmployee = DtoUtil.createEmployeeCollection();
    EmployeeCollection updatedEmployee = DtoUtil.createEmployeeCollection();
    updatedEmployee.setFirstName(request.firstName());
    EmployeeResponseDto expectedResponse =
        EmployeeMapperUtil.mapToEmployeeResponseDto(updatedEmployee);

    Mockito.when(employeeRepository.findByIdAndActiveTrue(employeeId))
        .thenReturn(Optional.of(existingEmployee));
    Mockito.when(employeeRepository.save(Mockito.any(EmployeeCollection.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    EmployeeResponseDto actualResponse = employeeService.updateEmployee(employeeId, request);

    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(expectedResponse.firstName(), actualResponse.firstName());
    Mockito.verify(employeeRepository).findByIdAndActiveTrue(employeeId);
    Mockito.verify(employeeRepository).save(Mockito.any(EmployeeCollection.class));
  }

  /**
   * Update employee fail.
   */
  @Test
  void updateEmployeeFail(){

    EmployeeRequestDto invalidRequest = EmployeeRequestDto.builder()
        .firstName("John")
        .birthDate(LocalDate.of(1990, 5, 15))
        .position("Software Engineer").build();

    Assertions.assertThrows(EmployeeException.class, () -> {
      new EmployeeServiceImpl(employeeRepository, validator, employeeProperties)
          .updateEmployee(employeeId, invalidRequest);
    });

    EmployeeNotFoundException exception =
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
          employeeService.updateEmployee(employeeId, DtoUtil.createEmployeeRequestDto());
        });
    Assertions.assertEquals(ConstantsError.EMPLOYEE_NOT_FOUND, exception.getMessage());
    Mockito.verify(employeeRepository).findByIdAndActiveTrue(employeeId);
    Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any());

  }

  /**
   * Insert employees return success list.
   */
  @Test
  void insertEmployeesReturnSuccessList() {
    EmployeeRequestDto validDto = DtoUtil.createEmployeeRequestDto();
    EmployeeCollection savedEntity = EmployeeMapperUtil.mapRequestToEmployee(validDto);
    savedEntity.setId(employeeId);

    Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(savedEntity);

    List<EmployeeRequestDto> employees = List.of(validDto);
    EmployeeInsertResponseDto response = employeeService.insertEmployees(employees);

    Assertions.assertEquals(1, response.success().size());
    Assertions.assertTrue(response.errors().isEmpty());
    Assertions.assertEquals(employeeId, response.success().getFirst().idEmployee());
  }

  /**
   * Insert employees validation exception.
   */
  @Test
  void insertEmployeesValidationException()  {

    EmployeeRequestDto invalidDto = EmployeeRequestDto.builder()
        .firstName("")
        .gender(GenderEnum.MALE)
        .birthDate(LocalDate.of(2020, 1, 1))
        .position("")
        .build();

    List<EmployeeRequestDto> employees2 = List.of(invalidDto);

    EmployeeValidationException exception = Assertions.assertThrows(
        EmployeeValidationException.class,
        () -> new EmployeeServiceImpl(employeeRepository, validator, employeeProperties)
            .insertEmployees(employees2)
    );

    Assertions.assertEquals(ConstantsError.EMPLOYEE_DATA_VALIDATION_FAILED, exception.getMessage());
    Assertions.assertTrue(exception.getEmployeeInsertResponseDto().success().isEmpty());
    Assertions.assertFalse(exception.getEmployeeInsertResponseDto().errors().isEmpty());
  }

  @Test
  void insertEmployeesValidationMongoException()  {


    List<EmployeeRequestDto> employees = List.of(DtoUtil.createEmployeeRequestDto());

    Mockito.when(employeeRepository.save(Mockito.any(EmployeeCollection.class)))
        .thenThrow(new MongoException("Simulated Mongo save failure"));

    EmployeeValidationException exception = Assertions.assertThrows(
        EmployeeValidationException.class,
        () -> employeeService.insertEmployees(employees)
    );

    Assertions.assertEquals(ConstantsError.EMPLOYEE_DATA_VALIDATION_FAILED, exception.getMessage());
    Assertions.assertTrue(exception.getEmployeeInsertResponseDto().success().isEmpty());
    Assertions.assertFalse(exception.getEmployeeInsertResponseDto().errors().isEmpty());
  }
}
