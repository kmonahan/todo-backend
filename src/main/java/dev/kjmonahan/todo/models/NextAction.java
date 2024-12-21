package dev.kjmonahan.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
public class NextAction {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Next action cannot be blank.")
    private String action;
    private boolean completed = false;
    private int priorityOrder;
    private Date dateCompleted;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;

    public NextAction() {}

    public NextAction(String action, int priorityOrder) {
        this();
        this.action = action;
        this.priorityOrder = priorityOrder;
    }

    public NextAction(String action, int priorityOrder, Project project) {
        this(action, priorityOrder);
        this.project = project;
    }

    public void toggleActionCompletion(boolean completed) {
        if (completed == this.isCompleted()) {
            return;
        }
        this.setCompleted(completed);
        if (completed) {
            this.setDateCompleted(new Date());
        } else {
            this.setDateCompleted(null);
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return this.getAction();
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
