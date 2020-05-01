package de.neuefische.todo.repository;
import de.neuefische.todo.model.TodoItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TodoRepository extends PagingAndSortingRepository<TodoItem, String> {

}
