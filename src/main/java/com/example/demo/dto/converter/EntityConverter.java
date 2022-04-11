package com.example.demo.dto.converter;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.TaskMapper;
import com.example.demo.dto.mapper.UserMapper;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

  private final UserMapper userMapper;
  private final TaskMapper taskMapper;

  public EntityConverter(UserMapper userMapper, TaskMapper todoMapper) {
    this.userMapper = userMapper;
    this.taskMapper = todoMapper;
  }

  public UserDto toDto(User user) {
    return userMapper.toDto(user);
  }

  public TaskDto toDto(Task task) {
    return taskMapper.toDto(task);
  }
}
