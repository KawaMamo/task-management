package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    private DescriptionStatus status;
    private Priority priority;
    @OneToOne
    private Person author;
    @OneToOne
    private Person performer;

    @OneToMany(mappedBy="task")
    @ToString.Exclude
    private Set<Comment> comments;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
