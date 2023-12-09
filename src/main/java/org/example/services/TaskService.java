package org.example.services;

import org.example.contract.requests.CreateTaskRequest;
import org.example.domain.DescriptionStatus;
import org.example.domain.Person;
import org.example.domain.Task;
import org.example.mappers.TaskMapper;
import org.example.repository.CommentRepository;
import org.example.repository.PersonRepository;
import org.example.repository.TaskRepository;
import org.example.specifications.FilterSpecifications;
import org.example.validators.TaskValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final CommentRepository commentRepository;
    private final TaskValidator taskValidator;
    private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepository, PersonRepository personRepository, CommentRepository commentRepository, TaskValidator taskValidator, TaskMapper mapper) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.commentRepository = commentRepository;
        this.taskValidator = taskValidator;
        this.mapper = mapper;
    }

    public Task createTask(CreateTaskRequest request){
        final Task task = mapper.requestToEntity(request);
        task.setCreatedAt(LocalDate.now());
        final Person author = personRepository.findById(request.getAuthorId()).orElseThrow(NoSuchElementException::new);
        final Person performer = personRepository.findById(request.getPerformerId()).orElseThrow(NoSuchElementException::new);
        final Task save = taskRepository.save(task);
        save.setAuthor(author);
        save.setPerformer(performer);
        return save;
    }

    public Task getTask(Long id){
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Task deleteTask(Long id){
        final Task task = taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
        taskValidator.checkOwnership(task);
        taskRepository.deleteById(id);
        return task;
    }

    public Task changeStatus(Long id, DescriptionStatus status){
        final Task task = taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
        taskValidator.validateUpdateStatus(task);
        task.setStatus(status);
        task.setUpdatedAt(LocalDate.now());
        return taskRepository.save(task);
    }
    public Task assignToPerformer(Long id, Long performerId){
        final Task task = taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
        taskValidator.checkOwnership(task);
        final Person performer = personRepository.findById(performerId).orElseThrow(NoSuchElementException::new);
        task.setPerformer(performer);
        return task;
    }

    public Page<Task> getTasks(FilterSpecifications<Task> specifications, Pageable pageable) {
        return taskRepository.findAll(specifications, pageable);
    }
}
