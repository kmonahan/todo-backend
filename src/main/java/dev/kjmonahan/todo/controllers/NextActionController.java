package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.dto.ProjectActionDTO;
import dev.kjmonahan.todo.models.NextAction;
import dev.kjmonahan.todo.models.Project;
import dev.kjmonahan.todo.repositories.NextActionRepository;
import dev.kjmonahan.todo.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
//        modelMapper
//                .typeMap(NextAction.class, ProjectActionDTO.class)
//                .addMapping(src -> src.getProject().getId(), ProjectActionDTO::setProjectId);
    }

    @GetMapping
    public Iterable<NextAction> getActions() {
        return actions.findByCompleted(false, Sort.by(Sort.Direction.ASC, "priorityOrder"));
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
    public ResponseEntity<NextAction> completeNextAction(@PathVariable int id, @Valid @RequestBody NextAction updatedAction) {
        Optional<NextAction> theAction = actions.findById(id);
        if (theAction.isPresent()) {
            NextAction nextAction = theAction.get();
            nextAction.setAction(updatedAction.getAction());
            nextAction.toggleActionCompletion(updatedAction.isCompleted());
            nextAction.setPriorityOrder(updatedAction.getPriorityOrder());
            nextAction.setProject(updatedAction.getProject());
            actions.save(nextAction);
            return new ResponseEntity<>(nextAction, HttpStatus.OK);
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
