/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.model.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.rod.trejo.employees.model.GenderEnum;

/**
 * The Record EmployeeResponseDto.
 *
 * @author rodTrejo.
 */
@Builder
public record EmployeeResponseDto(
    String idEmployee,
    String firstName,
    String middleName,
    String lastName,
    String secondLastName,
    GenderEnum gender,
    LocalDate birthDate,
    String position,
    Integer age) {
}


