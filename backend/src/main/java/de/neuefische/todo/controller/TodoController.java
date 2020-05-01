package de.neuefische.todo.controller;

import de.neuefische.todo.model.AddTodoItemData;
import de.neuefische.todo.model.TodoItem;
import de.neuefische.todo.model.UpdateStatusData;
import de.neuefische.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todo")
@AllArgsConstructor
public class TodoController {

  private TodoService service;

  @GetMapping
  public List<TodoItem> getAll() {
    return service.getAll();
  }

  @PostMapping
  public TodoItem add(@RequestBody AddTodoItemData item) {
    return service.add(item);
  }

  @DeleteMapping(path = "{id}")
  public void delete(@PathVariable String id) {
    service.deleteItem(id);
  }

  @PostMapping(path = "{id}/status")
  public ResponseEntity<TodoItem> updateStatus(@PathVariable String id, @RequestBody UpdateStatusData data) {
    Optional<TodoItem> updatedItem = service.updateStatus(id, data.getStatus());
    if (updatedItem.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedItem.get());
  }
}
