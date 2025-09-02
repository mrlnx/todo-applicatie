package nl.justid.todobackend.controller;

import nl.justid.todobackend.dto.TodoDto;
import nl.justid.todobackend.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

public class TodoControllerUnitTests {

    private final UUID fixedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private final UUID fixedId2 = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Order(1)
    @Test
    public void createTodo_Success() {
        // Arrange
        TodoDto inputTodoDto = new TodoDto();
        inputTodoDto.setId(fixedId);
        inputTodoDto.setName("Write unit tests");
        inputTodoDto.setDescription("Developers write tests to build more reliable and maintainable software by...");
        inputTodoDto.setDeadline(LocalDate.of(1986, 11, 11));
        inputTodoDto.setCompleted(false);

        TodoDto savedTodoDto = new TodoDto();
        inputTodoDto.setId(fixedId);
        savedTodoDto.setName("Write unit tests");
        savedTodoDto.setDescription("Developers write tests to build more reliable and maintainable software by...");
        savedTodoDto.setDeadline(LocalDate.of(1986, 11, 11));
        savedTodoDto.setCompleted(false);

        Mockito.when(todoService.createTodo(inputTodoDto)).thenReturn(savedTodoDto);

        // Act
        ResponseEntity<TodoDto> response = todoController.createTodo(inputTodoDto);

        // Assert
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(savedTodoDto, response.getBody());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).createTodo(inputTodoDto);
    }

    @Order(2)
    @Test
    public void getTodosById_Success() {
        // Arrange
        TodoDto mockedTodo = new TodoDto();
        mockedTodo.setId(fixedId);
        mockedTodo.setName("Write unit tests");
        mockedTodo.setDescription("Developers write tests to build more reliable and maintainable software by...");
        mockedTodo.setDeadline(LocalDate.of(1986, 11, 11));
        mockedTodo.setCompleted(false);

        Mockito.when(todoService.getTodoById(fixedId)).thenReturn(mockedTodo);

        // Act
        ResponseEntity<TodoDto> response = todoController.getTodoById(fixedId);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(mockedTodo, response.getBody());
    }

    @Order(3)
    @Test
    public void getAllTodos_Success() {
        // Arrange
        TodoDto mockedTodo1 = new TodoDto();
        mockedTodo1.setId(fixedId);
        mockedTodo1.setName("Write unit tests");
        mockedTodo1.setDescription("Developers write tests to build more reliable and maintainable software by...");
        mockedTodo1.setDeadline(LocalDate.of(1986, 11, 11));
        mockedTodo1.setCompleted(false);

        TodoDto mockedTodo2 = new TodoDto();
        mockedTodo2.setId(fixedId2);
        mockedTodo2.setName("Write e2e tests");
        mockedTodo2.setDescription("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...");
        mockedTodo2.setDeadline(LocalDate.of(1986, 11, 11));
        mockedTodo2.setCompleted(false);

        List<TodoDto> todos = Arrays.asList(mockedTodo1,mockedTodo2);
        Mockito.when(todoService.getAllTodos()).thenReturn(todos);

        // Act
        ResponseEntity<List<TodoDto>> response = todoController.getAllTodos();

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
        Assertions.assertTrue(response.getBody().contains(mockedTodo1));
        Assertions.assertTrue(response.getBody().contains(mockedTodo2));

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).getAllTodos();
    }

    @Order(4)
    @Test
    public void updateTodos_Success() {
        // Arrange
        TodoDto existingTodoDto = new TodoDto();
        existingTodoDto.setId(fixedId);
        existingTodoDto.setName("Write unit tests");
        existingTodoDto.setDescription("Developers write tests to build more reliable and maintainable software by...");
        existingTodoDto.setDeadline(LocalDate.of(1986, 11, 11));
        existingTodoDto.setCompleted(false);

        TodoDto updatedTodoDto = new TodoDto();
        updatedTodoDto.setName("Write e2e tests");
        updatedTodoDto.setDescription("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...");
        updatedTodoDto.setDeadline(LocalDate.of(1986, 1, 11));
        updatedTodoDto.setCompleted(true);

        Mockito.when(todoService.getTodoById(fixedId)).thenReturn(existingTodoDto);
        Mockito.when(todoService.updateTodo(fixedId, updatedTodoDto)).thenReturn(updatedTodoDto);

        // Act
        ResponseEntity<TodoDto> response = todoController.updateTodo(fixedId, updatedTodoDto);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertEquals("Write e2e tests", response.getBody().getName());
        Assertions.assertEquals("You write End-to-End (E2E) tests to simulate real user scenarios, ensuring that your entire application...", response.getBody().getDescription());
        Assertions.assertEquals(LocalDate.of(1986, 1, 11), response.getBody().getDeadline());
        Assertions.assertTrue(response.getBody().isCompleted());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).updateTodo(fixedId, updatedTodoDto);
    }

    // https://stackoverflow.com/questions/64151647/mockito-with-void-method-in-junit/64151700
    @Order(5)
    @Test
    public void deleteTodos_Success() {
        // Arrange
        Mockito.doNothing().when(todoService).deleteTodo(fixedId);

        // Act
        ResponseEntity<TodoDto> response = todoController.deleteTodo(fixedId);

        // Assert
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).deleteTodo(fixedId);
    }

    @Test
    @Order(6)
    void createTodo_InputNull() {
        // Arrange
        Mockito.when(todoService.createTodo(null)).thenReturn(null);

        // Act
        ResponseEntity<TodoDto> response = todoController.createTodo(null);

        // Assert
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).createTodo(null);
    }

    @Test
    @Order(7)
    void createTodo_ServiceThrowsException() {
        // Arrange
        TodoDto inputTodoDto = new TodoDto();
        inputTodoDto.setId(fixedId);
        inputTodoDto.setName("Write unit tests");
        inputTodoDto.setDescription("Developers write tests to build more reliable and maintainable software by...");
        inputTodoDto.setDeadline(LocalDate.of(1986, 11, 11));
        inputTodoDto.setCompleted(false);

        Mockito.when(todoService.createTodo(inputTodoDto)).thenThrow(new RuntimeException("Service Error"));

        // Act
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> todoController.createTodo(inputTodoDto));

        // Assert
        Assertions.assertEquals("Service Error", exception.getMessage());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).createTodo(inputTodoDto);
    }

    @Test
    @Order(8)
    void deleteTodo_ServiceThrowsException() {
        // Arrange
        Mockito.doThrow(new RuntimeException("Todo not found")).when(todoService).deleteTodo(fixedId);

        // Assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> todoController.deleteTodo(fixedId));
        Assertions.assertEquals("Todo not found", exception.getMessage());

        // Verify
        Mockito.verify(todoService, Mockito.times(1)).deleteTodo(fixedId);
    }
}
