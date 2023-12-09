package org.example.validators;

import org.example.domain.Task;
import org.example.security.UserDetails;

import java.util.Objects;

public class TaskValidator {
    public void validateUpdateStatus(Task task){
        if (!Objects.equals(UserDetails.UserId, task.getAuthor().getUserId()) && !Objects.equals(UserDetails.UserId, task.getPerformer().getUserId())) {
            throw new RuntimeException("unauthorized operation");
        }
    }

    public void checkOwnership(Task task){
        if(!Objects.equals(UserDetails.UserId, task.getAuthor().getUserId()))
            throw new RuntimeException("unauthorized operation");
    }
}
