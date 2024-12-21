package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.dto.ProjectActionDTO;
import dev.kjmonahan.todo.dto.ProjectDetailDTO;
import dev.kjmonahan.todo.dto.ProjectListDTO;
import dev.kjmonahan.todo.models.NextAction;
import dev.kjmonahan.todo.models.Project;
import dev.kjmonahan.todo.repositories.ProjectRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {
    @Autowired
    private ProjectRepository projects;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProjectController() {
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper
                .typeMap(NextAction.class, ProjectActionDTO.class)
                .addMapping(src -> src.getProject().getId(), ProjectActionDTO::setProjectId);
    }

    @GetMapping
    public ResponseEntity<List<ProjectListDTO>> getProjects() {
        List<ProjectListDTO> allProjects =
                StreamSupport
                        .stream(projects.findAll().spliterator(), false)
                        .map(project -> modelMapper.map(project, ProjectListDTO.class))
                        .toList();
        return new ResponseEntity<>(allProjects, null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectDetailDTO> createProject(@Valid @RequestBody Project newProject) {
        Project createdProject = projects.save(newProject);
        ProjectDetailDTO projectData = modelMapper.map(createdProject, ProjectDetailDTO.class);
        return new ResponseEntity<>(projectData, null, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjectDetailDTO> viewProject(@PathVariable int id) {
        Optional<Project> project = projects.findById(id);
        if (project.isPresent()) {
            Project foundProject = project.get();
            ProjectDetailDTO projectData = modelMapper.map(foundProject, ProjectDetailDTO.class);
            return new ResponseEntity<>(projectData, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<ProjectDetailDTO> editProject(@PathVariable int id, @Valid @RequestBody Project updatedProject) {
        Optional<Project> foundProject = projects.findById(id);
        if (foundProject.isPresent()) {
            Project project = foundProject.get();
            project.setTitle(updatedProject.getTitle());
            project.setEndGoal(updatedProject.getEndGoal());
            project.setNotes(updatedProject.getNotes());
            projects.save(project);
            ProjectDetailDTO projectData = modelMapper.map(project, ProjectDetailDTO.class);
            return new ResponseEntity<>(projectData, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable int id) {
        try {
            projects.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Missing project ID", null, HttpStatus.BAD_REQUEST);
        }
    }
}
