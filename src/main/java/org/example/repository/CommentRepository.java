package org.example.repository;

import org.example.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>,
        PagingAndSortingRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    Optional<List<Comment>> findAllByTaskId(long taskId);
}
