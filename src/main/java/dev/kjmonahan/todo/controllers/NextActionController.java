package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.dto.ProjectActionDTO;
import dev.kjmonahan.todo.models.NextAction;
import dev.kjmonahan.todo.models.Project;
import dev.kjmonahan.todo.repositories.NextActionRepository;
import dev.kjmonahan.todo.repositories.ProjectRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/next-action")
public class NextActionController {
    @Autowired
    private NextActionRepository actions;
    @Autowired
    private ProjectRepository projects;
    private final ModelMapper modelMapper = new ModelMapper();

    public NextActionController() {
        modelMapper
                .typeMap(NextAction.class, ProjectActionDTO.class)
                .addMapping(src -> src.getProject().getId(), ProjectActionDTO::setProjectId);
    }

    @GetMapping
    public Iterable<ProjectActionDTO> getActions() {
        return actions.findByCompleted(false, Sort.by(Sort.Direction.ASC, "priorityOrder")).stream()
                .map(action -> modelMapper.map(action, ProjectActionDTO.class))
                .toList();
    }

    @GetMapping(value = "/completed")
    public Iterable<NextAction> getCompletedActions() {
        return actions.findByCompleted(true, Sort.by(Sort.Direction.DESC, "dateCompleted"));
    }

    @PostMapping
    public ResponseEntity<ProjectActionDTO> createNextAction(@Valid @RequestBody ProjectActionDTO newAction) {
        NextAction fullNewAction = modelMapper.map(newAction, NextAction.class);
        Optional<Project> relatedProject = projects.findById(newAction.getProjectId());
        relatedProject.ifPresent(fullNewAction::setProject);
        actions.save(fullNewAction);
        return new ResponseEntity<>(modelMapper.map(fullNewAction, ProjectActionDTO.class), null, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<ProjectActionDTO> completeNextAction(@PathVariable int id, @Valid @RequestBody ProjectActionDTO updatedAction) {
        Optional<NextAction> theAction = actions.findById(id);
        if (theAction.isPresent()) {
            NextAction nextAction = theAction.get();
            nextAction.setAction(updatedAction.getAction());
            nextAction.toggleActionCompletion(updatedAction.isCompleted());
            nextAction.setPriorityOrder(updatedAction.getPriorityOrder());
            if (updatedAction.getProjectId() != 0 && projects.findById(updatedAction.getProjectId()).isPresent()) {
                nextAction.setProject(projects.findById(updatedAction.getProjectId()).get());
            }
            actions.save(nextAction);
            return new ResponseEntity<>(modelMapper.map(nextAction, ProjectActionDTO.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteNextAction(@PathVariable int id) {
        try {
            actions.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Missing action ID", null, HttpStatus.BAD_REQUEST);
        }
    }
}
