package org.example.repository;

import org.example.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends JpaRepository<Person, Long>,
        PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}
