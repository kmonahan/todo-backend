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
        return actions.findAll();
    }

    @PostMapping
    public ResponseEntity<NextAction> createNextAction(@Valid @RequestBody NextAction newAction) {
        actions.save(newAction);
        return new ResponseEntity<>(newAction, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/complete/{id}")
    public ResponseEntity<NextAction> completeNextAction(@PathVariable int id) {
        Optional<NextAction> theAction = actions.findById(id);
        if (theAction.isPresent()) {
            NextAction nextAction = theAction.get();
            nextAction.setCompleted(true);
            actions.save(nextAction);
            return new ResponseEntity<>(nextAction, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    }
}
