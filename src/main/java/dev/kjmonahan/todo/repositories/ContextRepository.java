package dev.kjmonahan.todo.repositories;

import dev.kjmonahan.todo.models.Context;
import org.springframework.data.repository.CrudRepository;

public interface ContextRepository extends CrudRepository<Context, Integer> {
}
