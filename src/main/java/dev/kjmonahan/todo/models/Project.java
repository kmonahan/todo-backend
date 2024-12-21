package dev.kjmonahan.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private int id;

    @NotEmpty(message = "Project title cannot be blank")
    private String title;
    private String endGoal;
    private String notes;
    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<NextAction> actions = new ArrayList<>();
    private boolean completed = false;
    @ManyToOne
    private Context context;

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

    public List<NextAction> getActions() {
        return actions;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
