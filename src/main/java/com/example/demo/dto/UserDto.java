package com.example.demo.dto;

import com.example.demo.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

// создать адаптеры с мапстракт

@Data
public class UserDto {

  private int id;
  private String username;
  private List<TodoDto> toDoList;

  public static UserDto toDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setToDoList(user.getToDoList().stream()
        .map(TodoDto::toDto)
        .collect(Collectors.toList()));
    return userDto;
  }
}
