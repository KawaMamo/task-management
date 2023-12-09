package org.example.mappers;

import org.example.contract.requests.CreateTaskRequest;
import org.example.domain.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {
    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "performerId", target = "performer.id")
    Task requestToEntity(CreateTaskRequest request);
}
