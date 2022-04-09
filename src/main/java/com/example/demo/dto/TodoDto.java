package com.example.demo.dto;

import com.example.demo.model.Todo;
import lombok.Data;

@Data
public class TodoDto {

  private int id;
  private String title;
  private boolean completed;

  public static TodoDto toDto(Todo todo) {
    TodoDto todoDto = new TodoDto();
    todoDto.setId(todo.getId());
    todoDto.setTitle(todo.getTitle());
    todoDto.setCompleted(todo.isCompleted());
    return todoDto;
  }
}
