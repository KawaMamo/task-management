package org.example.services;

import org.example.contract.requests.CreatePersonRequest;
import org.example.domain.Person;
import org.example.mappers.PersonMapper;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper mapper;

    public PersonService(PersonRepository personRepository, PersonMapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    public Person createPerson(CreatePersonRequest request, Long userId) {
        final Person person = mapper.toEntity(request);
        person.setUserId(userId);
        person.setCreatedAt(LocalDate.now());
        return personRepository.save(person);
    }
}
