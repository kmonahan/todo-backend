package dev.kjmonahan.todo.repositories;

import dev.kjmonahan.todo.models.NextAction;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NextActionRepository extends CrudRepository<NextAction, Integer> {
    List<NextAction> findByCompleted(boolean completed, Sort sort);
}
