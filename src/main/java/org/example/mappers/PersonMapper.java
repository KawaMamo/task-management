package org.example.mappers;

import org.example.contract.requests.CreatePersonRequest;
import org.example.domain.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    Person toEntity(CreatePersonRequest request);
}
