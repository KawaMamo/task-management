package org.example.contract.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Comment;
import org.example.domain.DescriptionStatus;
import org.example.domain.Priority;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private String title;
    private DescriptionStatus status;
    private Priority priority;
    private Long authorId;
    private Long performerId;
}
