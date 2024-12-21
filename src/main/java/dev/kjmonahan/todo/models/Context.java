package dev.kjmonahan.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Context {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "context", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Project> projects = new ArrayList<>();
    @NotEmpty(message = "Context title cannot be empty")
    private String title;

    public Context() {}

    public Context(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Context title cannot be empty") String title) {
        this.title = title;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public int getId() {
        return id;
    }
}
