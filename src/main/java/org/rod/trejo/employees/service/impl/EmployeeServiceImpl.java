/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.service.impl;

import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rod.trejo.employees.config.EmployeeProperties;
import org.rod.trejo.employees.constant.Constants;
import org.rod.trejo.employees.constant.ConstantsError;
import org.rod.trejo.employees.constant.ConstantsLog;
import org.rod.trejo.employees.exception.EmployeeException;
import org.rod.trejo.employees.exception.EmployeeNotFoundException;
import org.rod.trejo.employees.exception.EmployeeValidationException;
import org.rod.trejo.employees.model.EmployeeCollection;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;
import org.rod.trejo.employees.repository.EmployeeRepository;
import org.rod.trejo.employees.service.EmployeeService;
import org.rod.trejo.employees.util.EmployeeMapperUtil;
import org.springframework.stereotype.Service;

/**
 * The Class EmployeeServiceImpl.
 *
 * @author rodTrejo.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  /**
   * The Employee repository.
   */
  private final EmployeeRepository employeeRepository;

  /**
   * The Validator.
   */
  private final Validator validator;

  /**
   * The Employee properties.
   */
  private final EmployeeProperties employeeProperties;


  /**
   * {@inheritDoc}
   *
   * @return the all employees
   */
  @Override
  public List<EmployeeResponseDto> getAllEmployees() {
    return employeeRepository.findAllByActiveTrue().stream()
        .map(EmployeeMapperUtil::mapToEmployeeResponseDto).toList();
  }

  /**
   * {@inheritDoc}
   *
   * @param idEmployee the id employee
   */
  @Override
  public void deleteEmployee(String idEmployee) {

    Optional.ofNullable(idEmployee).filter(validIdEmployee())
        .map(validId ->
            employeeRepository.findById(validId)
                .map(existingEmployee -> {

                  existingEmployee.setActive(Constants.INACTIVE_EMPLOYEE);
                  EmployeeCollection savedEmployee = employeeRepository.save(existingEmployee);
                  log.info(ConstantsLog.EMPLOYEE_SUCCESSFULLY_DELETED, savedEmployee.getId());
                  return savedEmployee;

                }).orElseThrow(() ->
                    new EmployeeNotFoundException(ConstantsError.EMPLOYEE_NOT_FOUND)))
        .orElseThrow(() -> new EmployeeException(ConstantsError.EMPLOYEE_ID_IS_REQUIRED));
  }

  /**
   * {@inheritDoc}
   *
   * @param idEmployee      the id employee
   * @param employeeRequest the employee request
   * @return the employee response dto
   */
  @Override
  public EmployeeResponseDto updateEmployee(String idEmployee, EmployeeRequestDto employeeRequest) {

    Optional<String> validationError = validateEmployeeRequest(employeeRequest);
    if (validationError.isPresent()) {
      String errorMsg = validationError.get();
      log.error(ConstantsLog.VALIDATION_ERROR_FOR_EMPLOYEE, employeeRequest, errorMsg);
      throw new EmployeeException(errorMsg);
    }

    return Optional.of(idEmployee)
        .filter(validIdEmployee())
        .map(id -> employeeRepository.findById(id)
            .map(existingEmployee -> {

              EmployeeMapperUtil.mapRequestToEmployee(existingEmployee, employeeRequest);
              EmployeeCollection savedEmployee = employeeRepository.save(existingEmployee);
              log.info(ConstantsLog.EMPLOYEE_SUCCESSFULLY_UPDATED, savedEmployee.getId());
              return EmployeeMapperUtil.mapToEmployeeResponseDto(savedEmployee);

            }).orElseThrow(() -> new EmployeeNotFoundException(ConstantsError.EMPLOYEE_NOT_FOUND)))
        .orElseThrow(() -> new EmployeeException(ConstantsError.EMPLOYEE_ID_IS_REQUIRED));
  }

  /**
   * {@inheritDoc}
   *
   * @param employees the employees
   * @return the employee insert response dto
   */
  @Override
  public EmployeeInsertResponseDto insertEmployees(List<EmployeeRequestDto> employees) {

    List<EmployeeRequestDto> errors = new ArrayList<>();
    List<EmployeeResponseDto> success = employees.stream()
        .filter(requestDto -> {

          Optional<String> validationError = validateEmployeeRequest(requestDto);
          validationError.ifPresent(errorMsg -> {
            log.error(ConstantsLog.VALIDATION_ERROR_FOR_EMPLOYEE, requestDto, errorMsg);
            errors.add(
                EmployeeMapperUtil.mapEmployeeRequestDtoBuilder(requestDto).errorMessage(errorMsg)
                    .build());
          });
          return validationError.isEmpty();

        })
        .map(requestDto -> {
          try {
            EmployeeCollection saved =
                employeeRepository.save(EmployeeMapperUtil.mapRequestToEmployee(requestDto));
            log.info(ConstantsLog.EMPLOYEE_SAVED_SUCCESSFULLY, saved.getId());
            return EmployeeMapperUtil.mapToEmployeeResponseDto(saved);

          } catch (Exception ex) {
            log.error(ConstantsLog.EXCEPTION_SAVING_EMPLOYEE, requestDto, ex.getMessage());
            errors.add(EmployeeMapperUtil.mapEmployeeRequestDtoBuilder(requestDto)
                .errorMessage(ex.getMessage()).build());
            return null;
          }
        })
        .filter(Objects::nonNull).toList();

    EmployeeInsertResponseDto response = EmployeeInsertResponseDto.builder()
        .success(success)
        .errors(errors)
        .build();

    if (success.isEmpty()) {
      throw new EmployeeValidationException(
          ConstantsError.EMPLOYEE_DATA_VALIDATION_FAILED, response);
    }

    return response;
  }


  /**
   * Valid id employee predicate.
   *
   * @return the predicate
   */
  private Predicate<String> validIdEmployee() {
    return idEmployee -> Objects.nonNull(idEmployee) && !idEmployee.isBlank();
  }


  /**
   * Validate employee request optional.
   *
   * @param dto the dto
   * @return the optional
   */
  private Optional<String> validateEmployeeRequest(EmployeeRequestDto dto) {

    List<String> errors = new ArrayList<>();
    validateEmployeeAge(dto.birthDate())
        .ifPresent(err -> errors.add(Constants.BIRTH_DATE + Constants.SEPARATOR_MSG + err));

    validator.validate(dto).stream()
        .map(v ->
            v.getPropertyPath() + Constants.SEPARATOR_MSG + v.getMessage()
        )
        .forEach(errors::add);
    return errors.isEmpty()
        ? Optional.empty()
        : Optional.of(String.join(Constants.DELIMITER_MSG, errors));

  }

  /**
   * Validate employee age optional.
   *
   * @param birthDate the birthDate
   * @return the optional
   */
  private Optional<String> validateEmployeeAge(LocalDate birthDate) {
    if (Objects.isNull(birthDate)){
      return Optional.of(employeeProperties.getMessageErrorAgeValid());
    }

    int age = Period.between(birthDate, LocalDate.now()).getYears();
    if (age < employeeProperties.getMinimumAge() || age > employeeProperties.getMaximumAge()) {
      return Optional.of(employeeProperties.getMessageErrorAgeValid());
    }
    return Optional.empty();
  }

}
