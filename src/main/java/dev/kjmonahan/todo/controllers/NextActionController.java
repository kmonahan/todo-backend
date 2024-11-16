package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.models.NextAction;
import dev.kjmonahan.todo.repositories.NextActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public Iterable<NextAction> getActions() {
        return actions.findByCompleted(false);
    }

    @GetMapping(value = "/completed")
    public Iterable<NextAction> getCompletedActions() { return actions.findByCompleted(true); }

    @PostMapping
    public ResponseEntity<NextAction> createNextAction(@Valid @RequestBody NextAction newAction) {
        actions.save(newAction);
        return new ResponseEntity<>(newAction, null, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<NextAction> completeNextAction(@PathVariable int id, @Valid @RequestBody NextAction updatedAction) {
        Optional<NextAction> theAction = actions.findById(id);
        if (theAction.isPresent()) {
            NextAction nextAction = theAction.get();
            nextAction.setAction(updatedAction.getAction());
            nextAction.setCompleted(updatedAction.isCompleted());
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
