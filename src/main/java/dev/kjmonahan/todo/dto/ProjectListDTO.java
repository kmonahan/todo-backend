package dev.kjmonahan.todo.dto;

public class ProjectListDTO {
    private int id;
    private String title;

    public ProjectListDTO() {}

    public ProjectListDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
