/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import org.rod.trejo.employees.constant.ConstantsEmployeeApi;
import org.rod.trejo.employees.model.GenderEnum;

/**
 * The Record EmployeeRequestDto.
 *
 * @author rodTrejo.
 */
@Builder
public record EmployeeRequestDto(

    @NotBlank(message = "{firstName.notblank}") @Size(min = 2, max = 80,
        message = "{firstName.size}")
    String firstName,

    @Size(max = 80, message = "{middleName.size}")
    String middleName,

    @NotBlank(message = "{lastName.notblank}") @Size(max = 80,
        message = "{lastName.size}") String lastName,

    @Size(max = 80, message = "{secondLastName.size}")
    String secondLastName,

    @NotNull(message = "{gender.notnull}")
    @Enumerated(EnumType.STRING)
    GenderEnum gender,

    @NotNull(message = "{birthDate.notnull}")
    LocalDate birthDate,

    @NotBlank(message = "{position.notblank}") @Size(max = 100,
        message = "{position.size}")
    String position,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = ConstantsEmployeeApi.OPTIONAL_ERROR_MESSAGE, hidden = false)
    String errorMessage
) {
}
