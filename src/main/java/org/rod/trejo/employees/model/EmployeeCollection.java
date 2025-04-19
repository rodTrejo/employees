/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;
import lombok.Data;
import org.rod.trejo.employees.constant.Constants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class Employee.
 *
 * @author rodTrejo.
 */
@Document(collection = "employees")
@Data
public class EmployeeCollection {

  @Id
  private String id;

  private String firstName;

  private String middleName;

  private String lastName;

  private String secondLastName;

  private GenderEnum gender;

  private LocalDate birthDate;

  private String position;

  private Boolean active = true;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  /**
   * Gets age.
   *
   * @return the age
   */
  @Transient
  public int getAge() {
    return Objects.isNull(birthDate)
        ? Constants.INT_ZERO :
        Period.between(birthDate, LocalDate.now()).getYears();
  }

}
