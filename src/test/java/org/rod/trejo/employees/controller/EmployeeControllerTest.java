/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rod.trejo.employees.exception.EmployeeNotFoundException;
import org.rod.trejo.employees.exception.EmployeeValidationException;
import org.rod.trejo.employees.exception.advice.EmployeeControllerAdvice;
import org.rod.trejo.employees.model.dto.EmployeeInsertResponseDto;
import org.rod.trejo.employees.model.dto.EmployeeRequestDto;
import org.rod.trejo.employees.model.dto.EmployeeResponseDto;
import org.rod.trejo.employees.service.EmployeeService;
import org.rod.trejo.employees.util.Constants;
import org.rod.trejo.employees.util.DtoUtil;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * The Class EmployeeControllerTest.
 *
 * @author rodTrejo.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

  /**
   * The Employee controller.
   */
  @InjectMocks
  private EmployeeController employeeController;

  /**
   * The Employee service.
   */
  @Mock
  private EmployeeService employeeService;

  /**
   * The Message source.
   */
  @Mock
  private MessageSource messageSource;

  /**
   * The Mock mvc.
   */
  private MockMvc mockMvc;

  /**
   * The Object mapper.
   */
  private ObjectMapper objectMapper;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {

    this.objectMapper = Jackson2ObjectMapperBuilder.json().build();
    Mockito.lenient().when(
        messageSource.getMessage(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
            Mockito.any(Locale.class))).thenReturn(Constants.ERROR);

    mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
        .setControllerAdvice(new EmployeeControllerAdvice(messageSource))
        .addPlaceholderValue(Constants.API_BASE_PATH, Constants.API_PATH).build();
  }

  /**
   * Gets all employees ok.
   *
   * @throws Exception the exception
   */
  @Test
  void getAllEmployeesOK() throws Exception {
    EmployeeResponseDto employee = DtoUtil.createEmployeeResponseDto(UUID.randomUUID().toString());
    Mockito.when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

    mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_PATH))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));

    Mockito.verify(employeeService, Mockito.times(1)).getAllEmployees();
  }

  /**
   * Gets all employees mongo exception.
   *
   * @throws Exception the exception
   */
  @Test
  void getAllEmployeesMongoException() throws Exception {
    Mockito.when(employeeService.getAllEmployees()).thenThrow(MongoException.class);

    mockMvc.perform(MockMvcRequestBuilders.get(Constants.API_PATH))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.message").value(Constants.ERROR));

    Mockito.verify(employeeService, Mockito.times(1)).getAllEmployees();
  }

  /**
   * Delete employee ok.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteEmployeeOK() throws Exception {
    String employeeId = UUID.randomUUID().toString();
    Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);

    mockMvc.perform(MockMvcRequestBuilders
            .delete(Constants.API_PATH+"/{idEmployee}", employeeId))
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andExpect(MockMvcResultMatchers.content().string(""));

    Mockito.verify(employeeService, Mockito
        .times(1)).deleteEmployee(employeeId);
  }

  /**
   * Delete employee not found.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteEmployeeNotFound() throws Exception {
    String employeeId = UUID.randomUUID().toString();
    Mockito.doThrow(new EmployeeNotFoundException(Constants.EMPLOYEE_NOT_FOUND)).when(employeeService)
        .deleteEmployee(employeeId);

    mockMvc.perform(MockMvcRequestBuilders
            .delete(Constants.API_PATH+"/{idEmployee}", employeeId))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.
            jsonPath("$.detail").value(Constants.EMPLOYEE_NOT_FOUND))
        .andDo(MockMvcResultHandlers.print());

    Mockito.verify(employeeService, Mockito.times(1))
        .deleteEmployee(employeeId);
  }



  /**
   * Update employee ok.
   *
   * @throws Exception the exception
   */
  @Test
  void updateEmployeeOK() throws Exception {
    String employeeId = UUID.randomUUID().toString();
    EmployeeRequestDto employeeRequestDto = DtoUtil.createEmployeeRequestDto();

    EmployeeResponseDto updatedEmployee = DtoUtil.createEmployeeResponseDto(employeeId);

    Mockito.when(
            employeeService.updateEmployee(Mockito.anyString(),
                Mockito.any(EmployeeRequestDto.class))).thenReturn(updatedEmployee);

    mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_PATH+"/{idEmployee}",
                employeeId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employeeRequestDto)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.idEmployee").value(employeeId));

    Mockito.verify(employeeService, Mockito.times(1))
        .updateEmployee(Mockito.anyString(), Mockito.any(EmployeeRequestDto.class));
  }

  /**
   * Update employee not found.
   *
   * @throws Exception the exception
   */
  @Test
  void updateEmployeeNotFound() throws Exception {
    String employeeId = UUID.randomUUID().toString();
    EmployeeRequestDto employeeRequestDto = DtoUtil.createEmployeeRequestDto();

    Mockito.when(
            employeeService.updateEmployee(Mockito.anyString(),
                Mockito.any(EmployeeRequestDto.class)))
        .thenThrow(new EmployeeNotFoundException(Constants.EMPLOYEE_NOT_FOUND));

    mockMvc.perform(MockMvcRequestBuilders.put(Constants.API_PATH+"/{idEmployee}",
                employeeId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employeeRequestDto)))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail")
            .value(Constants.EMPLOYEE_NOT_FOUND));

    Mockito.verify(employeeService, Mockito.times(1))
        .updateEmployee(Mockito.anyString(), Mockito.any(EmployeeRequestDto.class));
  }

  /**
   * Insert employees ok.
   *
   * @throws Exception the exception
   */
  @Test
  void insertEmployeesOK() throws Exception {
    List<EmployeeRequestDto> employeeRequestDto =
        List.of(DtoUtil.createEmployeeRequestDto(), DtoUtil.createEmployeeRequestDto());

    EmployeeInsertResponseDto responseDto = new EmployeeInsertResponseDto(
        List.of(DtoUtil.createEmployeeResponseDto("2"),
            DtoUtil.createEmployeeResponseDto("1")),
        List.of());

    Mockito.when(employeeService.insertEmployees(Mockito.anyList())).thenReturn(responseDto);

    mockMvc.perform(
            MockMvcRequestBuilders.post(Constants.API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequestDto)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.success.length()").value(2))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.errors.length()").value(0));

    Mockito.verify(employeeService, Mockito.times(1))
        .insertEmployees(Mockito.anyList());
  }

  /**
   * Insert employees not found.
   *
   * @throws Exception the exception
   */
  @Test
  void insertEmployeesNotFound() throws Exception {

    EmployeeRequestDto invalidRequest = DtoUtil.createEmployeeRequestDto();

    List<EmployeeRequestDto> requests = List.of(invalidRequest);

    EmployeeInsertResponseDto responseDto =
        new EmployeeInsertResponseDto(List.of(), List.of(invalidRequest));

    Mockito.when(employeeService.insertEmployees(Mockito.anyList()))
        .thenThrow(new EmployeeValidationException(Constants.EMPLOYEE_NOT_FOUND, responseDto));

    mockMvc.perform(
            MockMvcRequestBuilders.post(Constants.API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests)))
        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()")
            .value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()")
            .value(1));

    Mockito.verify(employeeService, Mockito.times(1))
        .insertEmployees(Mockito.anyList());
  }

}
