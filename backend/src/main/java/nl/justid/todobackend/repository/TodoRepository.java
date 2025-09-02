package nl.justid.todobackend.repository;

import nl.justid.todobackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    Optional<Todo> findAllBy(UUID id);
}
