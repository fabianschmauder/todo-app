package de.neuefische.todo.controller;

import de.neuefische.todo.model.AddTodoItemData;
import de.neuefische.todo.model.TodoItem;
import de.neuefische.todo.model.TodoStatus;
import de.neuefische.todo.model.UpdateStatusData;
import de.neuefische.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private TodoRepository repository;

  @BeforeEach
  void resetDb() {
    repository.deleteAll();
  }

  private String baseUrl() {
    return "http://localhost:" + port + "/api/todo";
  }

  @Test
  void listAllItemsShouldReturnAllItemsFromDb() throws Exception {
    // GIVEN
    repository.save(new TodoItem("1", "some description", TodoStatus.OPEN));

    // WHEN
    ResponseEntity<TodoItem[]> response = restTemplate.getForEntity(
        new URL(baseUrl()).toString(), TodoItem[].class);

    // THEN
    TodoItem[] body = response.getBody();
    assertEquals(1, body.length);
    assertThat(body[0]).isEqualTo(new TodoItem("1", "some description", TodoStatus.OPEN));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  void addItemShouldAddToDatabase() throws Exception {

    // WHEN
    ResponseEntity<TodoItem> response = restTemplate.postForEntity(
        new URL(baseUrl()).toString(),
        new AddTodoItemData("some description"), TodoItem.class);

    // THEN
    TodoItem responseItem = response.getBody();

    Optional<TodoItem> dbSaved = repository.findById(responseItem.getId());

    assertThat(responseItem).isEqualTo(dbSaved.get());
    assertThat(responseItem.getStatus()).isEqualTo(TodoStatus.OPEN);
    assertThat(responseItem.getDescription()).isEqualTo("some description");
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void deleteItemShouldRemoveFromDatabase() throws Exception {
    // GIVEN
    repository.save(new TodoItem("1", "some description", TodoStatus.OPEN));

    // WHEN
    restTemplate.delete(new URL(baseUrl() + "/1").toString());

    // THEN
    Optional<TodoItem> item = repository.findById("1");

    assertThat(item.isPresent()).isFalse();
  }

  @Test
  void setStatusShouldUpdateItem() throws Exception {
    // GIVEN
    repository.save(new TodoItem("1", "some description", TodoStatus.OPEN));

    // WHEN
    ResponseEntity<TodoItem> response = restTemplate.postForEntity(
        new URL(baseUrl()).toString()+"/1/status",
        new UpdateStatusData(TodoStatus.DONE), TodoItem.class);

    // THEN
    Optional<TodoItem> item = repository.findById("1");

    assertThat(item.get()).isEqualTo(new TodoItem("1", "some description", TodoStatus.DONE));
    assertThat(response.getBody()).isEqualTo(new TodoItem("1", "some description", TodoStatus.DONE));
  }
}
