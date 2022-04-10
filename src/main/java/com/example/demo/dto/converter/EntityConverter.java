package com.example.demo.dto.converter;

import com.example.demo.dto.TodoDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.TodoMapper;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

  private final UserMapper userMapper;
  private final TodoMapper todoMapper;

  public EntityConverter(UserMapper userMapper, TodoMapper todoMapper) {
    this.userMapper = userMapper;
    this.todoMapper = todoMapper;
  }

  public UserDto toDto(User user) {
    return userMapper.toDto(user);
  }

  public TodoDto toDto(Todo todo) {
    return todoMapper.toDto(todo);
  }
}
