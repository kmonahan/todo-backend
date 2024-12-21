package dev.kjmonahan.todo.dto;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectDetailDTO {
    private int id;
    private String title;
    private List<ProjectActionDTO> actions;
    private String endGoal;
    private String notes;

    public ProjectDetailDTO() {}

    public ProjectDetailDTO(int id, String title, List<ProjectActionDTO> actions) {
        this.id = id;
        this.title = title;
        this.actions = actions;
    }

    public ProjectDetailDTO(int id, String title, List<ProjectActionDTO> actions, String endGoal) {
        this(id, title, actions);
        this.endGoal = endGoal;
    }

    public ProjectDetailDTO(int id, String title, List<ProjectActionDTO> actions, String endGoal, String notes) {
        this(id, title, actions, endGoal);
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<ProjectActionDTO> getActions() {
       actions.removeIf(ProjectActionDTO::isCompleted);
       return actions;
    }

    public String getEndGoal() {
        return endGoal;
    }

    public String getNotes() {
        return notes;
    }
}
