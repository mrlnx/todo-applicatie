package nl.justid.todobackend.controller;

import nl.justid.todobackend.dto.TodoDto;
import nl.justid.todobackend.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable UUID id) {
        TodoDto todoDto = todoService.getTodoById(id);
        return ResponseEntity.ok(todoDto);
    }

    @PostMapping()
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        TodoDto createdTodoDto = todoService.createTodo(todoDto);
        return new ResponseEntity<>(createdTodoDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable UUID id, @RequestBody TodoDto todoDto) {
        TodoDto updatedTodoDto = todoService.updateTodo(id, todoDto);
        return new ResponseEntity<>(updatedTodoDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoDto> deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
