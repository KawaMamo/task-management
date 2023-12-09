package org.example.services;

import org.example.contract.requests.CreateCommentRequest;
import org.example.domain.Comment;
import org.example.domain.Person;
import org.example.domain.Task;
import org.example.mappers.CommentMapper;
import org.example.repository.CommentRepository;
import org.example.repository.PersonRepository;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, PersonRepository personRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    public Comment createCommit(@RequestBody CreateCommentRequest request){
        final Comment comment = commentMapper.toEntity(request);
        comment.setCreatedAt(LocalDate.now());
        final Person person = personRepository.findById(request.getPersonId()).orElseThrow(() -> new NoSuchElementException("Could not find this person"));
        final Task task = taskRepository.findById(request.getTaskId()).orElseThrow(() -> new NoSuchElementException("Task could not be found"));
        final Comment save = commentRepository.save(comment);
        save.setTask(task);
        save.setPerson(person);
        return save;
    }
}
