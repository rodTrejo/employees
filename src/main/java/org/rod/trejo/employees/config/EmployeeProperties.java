/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.config;

import java.text.MessageFormat;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rod.trejo.employees.constant.ConstantsError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class EmployeeProperties.
 *
 * @author rodTrejo.
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EmployeeProperties {

  /**
   * The Minimum age.
   */
  @Value("${employee.age.minimum}")
  private int minimumAge;

  /**
   * The Maximum age.
   */
  @Value("${employee.age.maximum}")
  private int maximumAge;

  /**
   * The Message error age.
   */
  @Value("${employee.age.msg}")
  private String messageErrorAge;

  /**
   * Gets message error age valid.
   *
   * @return the message error age valid
   */
  public String getMessageErrorAgeValid() {
    return Objects.isNull(this.messageErrorAge)
        ? ConstantsError.AGE_BETWEEN_YEARS_OLD
        : MessageFormat.format(
            this.messageErrorAge,
            this.minimumAge,
            this.maximumAge
        );
  }
}
