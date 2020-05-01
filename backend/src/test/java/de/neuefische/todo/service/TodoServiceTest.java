package de.neuefische.todo.service;

import de.neuefische.todo.model.AddTodoItemData;
import de.neuefische.todo.model.TodoItem;
import de.neuefische.todo.model.TodoStatus;
import de.neuefische.todo.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TodoService.class)
class TodoServiceTest {

  @Autowired
  TodoService todoService;

  @MockBean
  TodoRepository repository;

  @Test
  @DisplayName("Get all should return items from repository")
  void getAllFromRepository() {
    ArrayList<TodoItem> items = new ArrayList<>();
    items.add(new TodoItem("1", "some desc", TodoStatus.OPEN));
    items.add(new TodoItem("2", "some other desc", TodoStatus.DONE));
    when(repository.findAll()).thenReturn(items);

    List<TodoItem> result = todoService.getAll();

    assertThat(result).containsSequence(
        new TodoItem("1", "some desc", TodoStatus.OPEN),
        new TodoItem("2", "some other desc", TodoStatus.DONE));
  }


  @Test
  @DisplayName("Add should add item to repository")
  void addShouldAddItemToRepository() {

    var result = todoService.add(new AddTodoItemData("some description"));

    assertThat(result.getDescription()).isEqualTo("some description");
    assertThat(result.getId()).isNotNull();
    assertThat(result.getStatus()).isEqualTo(TodoStatus.OPEN);

    ArgumentCaptor<TodoItem> captor = ArgumentCaptor.forClass(TodoItem.class);

    verify(repository).save(captor.capture());

    var saved = captor.getValue();
    assertThat(saved.getDescription()).isEqualTo("some description");
    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getStatus()).isEqualTo(TodoStatus.OPEN);
  }

  @Test
  @DisplayName("Delete should delete item from repository")
  void deleteShouldDeleteItemFromRepository() {
    todoService.deleteItem("someId");

    verify(repository).deleteById("someId");
  }


  @Test
  @DisplayName("Update status should update item")
  void updateStatusShouldUpdateItemFromRepository() {

    TodoItem value = new TodoItem("someId", "some desc", TodoStatus.OPEN);
    when(repository.findById("someId")).thenReturn(Optional.of(value));
    when(repository.save(eq(new TodoItem("someId", "some desc", TodoStatus.DONE)))).thenReturn(new TodoItem("someId", "some desc", TodoStatus.DONE));

    var saved = todoService.updateStatus("someId" , TodoStatus.DONE);

    verify(repository).save(eq(new TodoItem("someId", "some desc", TodoStatus.DONE)));
    assertThat(saved.get()).isEqualTo(new TodoItem("someId", "some desc", TodoStatus.DONE));
  }

  @Test
  @DisplayName("Update status should return optional empty when item not exists ")
  void updateStatusShouldReturnOptionalEmptyWhenItemDoesNotExist() {

    when(repository.findById("someId")).thenReturn(Optional.empty());

    var saved = todoService.updateStatus("someId" , TodoStatus.DONE);

    assertThat(saved.isEmpty()).isTrue();
    verify(repository, times(0)).save(any());
  }
}
