package com.badradstorm.tasklist.dto.converter;

import com.badradstorm.tasklist.dto.mapper.TaskMapper;
import com.badradstorm.tasklist.dto.mapper.UserMapper;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.dto.TaskDto;
import com.badradstorm.tasklist.dto.UserDto;
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
