package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.models.NextAction;
import dev.kjmonahan.todo.repositories.NextActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
}
