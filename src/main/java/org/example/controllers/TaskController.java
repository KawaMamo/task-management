package org.example.controllers;

import org.example.contract.requests.CreateTaskRequest;
import org.example.domain.DescriptionStatus;
import org.example.domain.Task;
import org.example.services.TaskService;
import org.example.specifications.CriteriaArrayToList;
import org.example.specifications.FilterSpecifications;
import org.example.specifications.SearchCriteria;
import org.example.specifications.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    Task createTask(@RequestBody CreateTaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping
    Task getTask(Long id){
        return taskService.getTask(id);
    }

    @DeleteMapping
    Task deleteTask(Long id){
        return taskService.deleteTask(id);
    }

    @PatchMapping("/changeStatus")
    Task changeStatus(Long id, DescriptionStatus status){
        return taskService.changeStatus(id, status);
    }

    @PatchMapping("/assignPerformer")
    Task assignToPerformer(Long id, Long performerId){
        return taskService.assignToPerformer(id, performerId);
    }

    @GetMapping("/filter")
    Page<Task> getAllTasks(SearchFilter filter, Pageable pageable){
        final List<SearchCriteria> criteriaList = CriteriaArrayToList.getCriteriaList(filter);
        final FilterSpecifications<Task> specifications = new FilterSpecifications<>(criteriaList);
        return taskService.getTasks(specifications, pageable);
    }
}
