package de.neuefische.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todo")
@Data
public class TodoItem {
    @Id
    private String id;
    private String description;
    private TodoStatus status;
}
