package dev.kjmonahan.todo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Project title cannot be blank")
    private String title;
    private String endGoal;
    private String notes;
    @OneToMany(mappedBy = "project")
    private final List<NextAction> actions = new ArrayList<>();
    private boolean completed = false;

    public Project() {}

    public Project(String title) {
        this();
        this.title = title;
    }

    public Project(String title, String endGoal) {
        this(title);
        this.endGoal = endGoal;
    }

    public Project(String title, String endGoal, String notes) {
        this(title, endGoal);
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!title.isEmpty()) {
            this.title = title;
        }
    }

    public String getEndGoal() {
        return endGoal;
    }

    public void setEndGoal(String endGoal) {
        this.endGoal = endGoal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }
}
