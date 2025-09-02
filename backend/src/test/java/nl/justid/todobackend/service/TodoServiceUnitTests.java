package nl.justid.todobackend.service;

import nl.justid.todobackend.dto.TodoDto;
import nl.justid.todobackend.entity.Todo;
import nl.justid.todobackend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceUnitTests {

    private final UUID fixedId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private final UUID fixedId2 = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo1;
    private Todo todo2;

    @BeforeEach
    void setUp() {
        todo1 = new Todo();
        todo1.setId(fixedId1);
        todo1.setName("Write unit tests");
        todo1.setDescription("Developers write tests to build more reliable and maintainable software by...");
        todo1.setDeadline(LocalDate.of(1986, 11, 11));
        todo1.setCompleted(false);

        todo2 = new Todo();
        todo2.setId(fixedId2);
        todo2.setName("Write e2e tests");
        todo2.setDescription("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...");
        todo2.setDeadline(LocalDate.of(1990, 1, 1));
        todo2.setCompleted(true);
    }

    @Test
    void getAllTodos_Success() {
        // Arrange
        Mockito.when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // Act
        List<TodoDto> result = todoService.getAllTodos();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        TodoDto dto1 = result.get(0);
        TodoDto dto2 = result.get(1);

        Assertions.assertEquals(fixedId1, dto1.getId());
        Assertions.assertEquals("Write unit tests", dto1.getName());
        Assertions.assertEquals("Developers write tests to build more reliable and maintainable software by...", dto1.getDescription());
        Assertions.assertEquals(LocalDate.of(1986, 11, 11), dto1.getDeadline());
        Assertions.assertFalse(dto1.isCompleted());

        Assertions.assertEquals(fixedId2, dto2.getId());
        Assertions.assertEquals("Write e2e tests", dto2.getName());
        Assertions.assertEquals("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...", dto2.getDescription());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), dto2.getDeadline());
        Assertions.assertTrue(dto2.isCompleted());

        // Verify
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.add(new TodoDto()));
    }

    @Test
    void getAllTodos_Empty() {
        // Arrange
        Mockito.when(todoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<TodoDto> result = todoService.getAllTodos();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getTodoById_NotFoundThrows() {
        // Arrange
        Mockito.when(todoRepository.findById(fixedId1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> {
            todoService.getTodoById(fixedId1);
        });
        Assertions.assertTrue(ex.getMessage().contains("Can't find todo by id"));

        // Verify
        Mockito.verify(todoRepository, Mockito.times(1)).findById(fixedId1);
    }

    @Test
    void updateTodo_Success() {

        // Arrange
        Todo existing = new Todo();
        existing.setId(fixedId1);
        existing.setName("Write unit tests");
        existing.setDescription("Developers write tests to build more reliable and maintainable software by...");
        existing.setDeadline(LocalDate.of(1980, 1, 1));
        existing.setCompleted(false);

        Mockito.when(todoRepository.findById(fixedId1)).thenReturn(Optional.of(existing));
        Mockito.when(todoRepository.save(Mockito.any(Todo.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        TodoDto updates = new TodoDto();
        updates.setName("Write e2e tests");
        updates.setDescription("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...");
        updates.setDeadline(LocalDate.of(1999, 12, 31));
        updates.setCompleted(true);

        // Act
        // https://stackoverflow.com/questions/70590208/proper-usage-of-mockito-argumentcaptor-in-java
        TodoDto result = todoService.updateTodo(fixedId1, updates);
        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);

        // Verify
        Mockito.verify(todoRepository, Mockito.times(1)).save(captor.capture());

        Todo saved = captor.getValue();

        Assertions.assertEquals(fixedId1, saved.getId());
        Assertions.assertEquals("Write e2e tests", saved.getName());
        Assertions.assertEquals("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...", saved.getDescription());
        Assertions.assertEquals(LocalDate.of(1999, 12, 31), saved.getDeadline());
        Assertions.assertTrue(saved.isCompleted());

        Assertions.assertEquals(fixedId1, result.getId());
        Assertions.assertEquals("Write e2e tests", result.getName());
    }

    @Test
    void createTodo_Success() {
        // Arrange
        TodoDto input = new TodoDto();
        input.setId(fixedId1);
        input.setName("Write unit tests");
        input.setDescription("Developers write tests to build more reliable and maintainable software by...");
        input.setDeadline(LocalDate.of(2025, 1, 1));
        input.setCompleted(false);

        Mockito.when(todoRepository.save(Mockito.any(Todo.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        TodoDto result = todoService.createTodo(input);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(fixedId1, result.getId());
        Assertions.assertEquals("Write unit tests", result.getName());
        Assertions.assertEquals("Developers write tests to build more reliable and maintainable software by...", result.getDescription());
        Assertions.assertEquals(LocalDate.of(2025, 1, 1), result.getDeadline());
        Assertions.assertFalse(result.isCompleted());

        // Verify
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
    }

    @Test
    void deleteTodo_NotFoundThrows() {
        // Arrange
        Mockito.when(todoRepository.findById(fixedId1)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> {
            todoService.deleteTodo(fixedId1);
        });
        Assertions.assertTrue(ex.getMessage().contains("Can't find todo by id"));

        // Verify
        Mockito.verify(todoRepository, Mockito.times(1)).findById(fixedId1);
        Mockito.verify(todoRepository, Mockito.never()).delete(Mockito.any());
    }
}
