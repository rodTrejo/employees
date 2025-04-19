/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.model.dto;

import java.util.List;
import lombok.Builder;

/**
 * The Class EmployeeInsertResponseDto.
 *
 * @author rodTrejo.
 */
@Builder
public record EmployeeInsertResponseDto(
    List<EmployeeResponseDto> success,
    List<EmployeeRequestDto> errors
) {}
