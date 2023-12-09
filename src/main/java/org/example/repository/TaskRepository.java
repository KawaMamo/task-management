package org.example.repository;

import org.example.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends JpaRepository<Task, Long>,
        PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}
