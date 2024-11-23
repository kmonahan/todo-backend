package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.models.Project;
import dev.kjmonahan.todo.repositories.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {
    @Autowired
    private ProjectRepository projects;

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project newProject) {
        projects.save(newProject);
        return new ResponseEntity<>(newProject, null, HttpStatus.CREATED);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Project> viewProject(@PathVariable int id) {
        Optional<Project> project = projects.findById(id);
        return project.map(value -> new ResponseEntity<>(value, null, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value="/edit/{id}")
    public ResponseEntity<Project> editProject(@PathVariable int id, @Valid @RequestBody Project updatedProject) {
        Optional<Project> foundProject = projects.findById(id);
        if (foundProject.isPresent()) {
            Project project = foundProject.get();
            project.setTitle(updatedProject.getTitle());
            project.setEndGoal(updatedProject.getEndGoal());
            project.setNotes(updatedProject.getNotes());
            projects.save(project);
            return new ResponseEntity<>(project, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
