package dev.kjmonahan.todo.repositories;

import dev.kjmonahan.todo.models.NextAction;
import org.springframework.data.repository.CrudRepository;

public interface NextActionRepository extends CrudRepository<NextAction, Integer> {
}
