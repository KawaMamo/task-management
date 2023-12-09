package org.example.mappers;

import org.example.contract.requests.CreateCommentRequest;
import org.example.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(source = "personId", target = "person.id")
    @Mapping(source = "taskId", target = "task.id")
    Comment toEntity(CreateCommentRequest request);
}
