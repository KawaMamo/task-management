package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private Person person;
    private String text;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
