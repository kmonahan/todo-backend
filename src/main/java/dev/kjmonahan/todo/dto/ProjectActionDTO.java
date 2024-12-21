package dev.kjmonahan.todo.dto;

public class ProjectActionDTO {
    private int id;
    private String action;
    private int priorityOrder;
    private boolean completed = false;
    private int projectId;

    public ProjectActionDTO() {}

    public ProjectActionDTO(int id, String action, int projectId, int priorityOrder) {
        this.id = id;
        this.action = action;
        this.projectId = projectId;
        this.priorityOrder = priorityOrder;
    }

    public ProjectActionDTO(int id, String action, int projectId, int priorityOrder, boolean completed) {
        this(id, action, projectId, priorityOrder);
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public int getPriorityOrder() {
        return priorityOrder;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

}
