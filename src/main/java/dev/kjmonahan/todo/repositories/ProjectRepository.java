package dev.kjmonahan.todo.repositories;

import dev.kjmonahan.todo.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
