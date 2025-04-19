/*
 * Copyright (c) 2025. Rodrigo Noé Trejo Guerra.
 * This software may be copied, modified, and distributed freely under the terms of the
 * MIT license.
 */

package org.rod.trejo.employees.repository;

import java.util.List;
import java.util.Optional;
import org.rod.trejo.employees.model.EmployeeCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The interface EmployeeRepository.
 *
 * @author rodTrejo.
 */
public interface EmployeeRepository extends MongoRepository<EmployeeCollection, String> {

  List<EmployeeCollection> findAllByActiveTrue();

  Optional<EmployeeCollection> findByIdAndActiveTrue(String id);
}
