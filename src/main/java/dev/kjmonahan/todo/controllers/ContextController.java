package dev.kjmonahan.todo.controllers;

import dev.kjmonahan.todo.models.Context;
import dev.kjmonahan.todo.repositories.ContextRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/context")
public class ContextController {
    @Autowired
    private ContextRepository contextRepository;

    @PostMapping(value = "/create")
    public ResponseEntity<Context> createContext(@Valid @RequestBody Context context) {
        Context createdContext = contextRepository.save(context);
        return new ResponseEntity<>(createdContext, null, HttpStatus.CREATED);
    }
}
