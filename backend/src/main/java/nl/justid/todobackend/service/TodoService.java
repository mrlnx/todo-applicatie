
package nl.justid.todobackend.service;

import nl.justid.todobackend.dto.TodoDto;
import nl.justid.todobackend.entity.Todo;
import nl.justid.todobackend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(this::mapToDto).toList();
    }
    public TodoDto getTodoById(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find todo by id: " + id));
        return mapToDto(todo);
    }
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = mapToEntity(todoDto);
        Todo createdTodo = todoRepository.save(todo);
        return mapToDto(createdTodo);
    }
    public TodoDto updateTodo(UUID id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find todo by id: " + id));

        todo.setName(todoDto.getName());
        todo.setDescription(todoDto.getDescription());
        todo.setTags(todoDto.getTags());
        todo.setDeadline(todoDto.getDeadline());
        todo.setCompleted(todoDto.isCompleted());

        todoRepository.save(todo);

        return mapToDto(todo);
    }
    public void deleteTodo(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find todo by id: " + id));

        todoRepository.delete((todo));
    }

    public TodoDto mapToDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setName(todo.getName());
        todoDto.setDescription(todo.getDescription());
        todoDto.setTags(todo.getTags());
        todoDto.setDeadline(todo.getDeadline());
        todoDto.setCreatedAt(todo.getCreatedAt());
        todoDto.setCompleted(
                todo.isCompleted()
        );
        return todoDto;
    }

    public Todo mapToEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setName(todoDto.getName());
        todo.setDescription(todoDto.getDescription());
        todo.setTags(todoDto.getTags());
        todo.setDeadline(todoDto.getDeadline());
        todo.setCreatedAt(todoDto.getCreatedAt());
        todo.setCompleted(todoDto.isCompleted());
        return todo;
    }
}
