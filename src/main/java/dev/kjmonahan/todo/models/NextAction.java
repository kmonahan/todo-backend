package dev.kjmonahan.todo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class NextAction {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Next action cannot be blank.")
    private String action;

    public NextAction() {}

    public NextAction(String action) {
        this();
        this.action = action;
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
}
