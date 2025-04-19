/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.model.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * The Record ErrorResponseDto.
 *
 * @author rodTrejo.
 */
@Builder
public record ErrorResponseDto(
    String code,
    String message,
    String detail,
    LocalDateTime timestamp,
    String type
) {}
