package de.neuefische.todo.controller;

import de.neuefische.todo.model.AddTodoItemData;
import de.neuefische.todo.model.TodoItem;
import de.neuefische.todo.model.TodoStatus;
import de.neuefische.todo.model.UpdateStatusData;
import de.neuefische.todo.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TodoController.class)
class TodoControllerTest {
  @Autowired
  TodoController todoController;

  @MockBean
  TodoService todoService;


  @Test
  @DisplayName("Get all should return items from service")
  void getAll() {
    ArrayList<TodoItem> items = new ArrayList<>();
    items.add(new TodoItem("1", "some desc", TodoStatus.OPEN));
    items.add(new TodoItem("2", "some other desc", TodoStatus.DONE));
    when(todoService.getAll()).thenReturn(items);

    List<TodoItem> result = todoController.getAll();

    assertThat(result).containsSequence(
        new TodoItem("1", "some desc", TodoStatus.OPEN),
        new TodoItem("2", "some other desc", TodoStatus.DONE));
  }

  @Test
  @DisplayName("add should service add")
  void addItem() {
    when(todoService.add(eq(new AddTodoItemData("some description")))).thenReturn(new TodoItem("1", "some description", TodoStatus.OPEN));
    var result = todoController.add(new AddTodoItemData("some description"));

    assertThat(result.getDescription()).isEqualTo("some description");
    assertThat(result.getId()).isNotNull();
    assertThat(result.getStatus()).isEqualTo(TodoStatus.OPEN);
    verify(todoService).add(eq(new AddTodoItemData("some description")));
  }


  @Test
  @DisplayName("delete should service delete item")
  void deleteItem() {
    todoController.delete("id");
    verify(todoService).deleteItem("id");
  }

  @Test
  @DisplayName("update status should return updated item")
  void updateStatusForItem() {
    when(todoService.updateStatus("id", TodoStatus.DONE)).thenReturn(Optional.of(new TodoItem("id", "some description", TodoStatus.DONE)));

    ResponseEntity<TodoItem> response = todoController.updateStatus("id", new UpdateStatusData(TodoStatus.DONE));

    verify(todoService).updateStatus("id", TodoStatus.DONE);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(new TodoItem("id", "some description", TodoStatus.DONE));
  }

  @Test
  @DisplayName("update status should return not found when item not exists")
  void updateStatusItemNotFound() {
    when(todoService.updateStatus("id", TodoStatus.DONE)).thenReturn(Optional.empty());

    ResponseEntity<TodoItem> response = todoController.updateStatus("id", new UpdateStatusData(TodoStatus.DONE));

    verify(todoService).updateStatus("id", TodoStatus.DONE);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
