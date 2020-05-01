package de.neuefische.todo.service;

import de.neuefische.todo.model.AddTodoItemData;
import de.neuefische.todo.model.TodoItem;
import de.neuefische.todo.model.TodoStatus;
import de.neuefische.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TodoService {

  private TodoRepository repository;

  public List<TodoItem> getAll() {
    var arrayList = new ArrayList<TodoItem>();
    var all = repository.findAll();
    all.forEach(arrayList::add);
    return arrayList;
  }

  public TodoItem add(AddTodoItemData data) {
    TodoItem item = new TodoItem(UUID.randomUUID().toString(), data.getDescription(), TodoStatus.OPEN);
    repository.save(item);
    return item;
  }

  public void deleteItem(String id) {
    repository.deleteById(id);
  }

  public Optional<TodoItem> updateStatus(String id, TodoStatus status) {
    Optional<TodoItem> optionalTodoItem = repository.findById(id);
    if (optionalTodoItem.isEmpty()) {
      return Optional.empty();
    }
    TodoItem item = optionalTodoItem.get();
    item.setStatus(status);
    return Optional.of(repository.save(item));
  }
}
