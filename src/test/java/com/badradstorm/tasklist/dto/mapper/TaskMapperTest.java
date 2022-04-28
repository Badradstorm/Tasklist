package com.badradstorm.tasklist.dto.mapper;

import com.badradstorm.tasklist.dto.response.TaskDto;
import com.badradstorm.tasklist.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskMapperTest {

  private final TaskMapper taskMapper = new TaskMapperImpl();

  @Test
  void toDto() {
    Task task = new Task();
    task.setId(1);
    task.setCompleted(true);
    task.setTitle("title");

    TaskDto taskDto = taskMapper.toDto(task);

    Assertions.assertEquals(task.getId(), taskDto.getId());
    Assertions.assertEquals(task.getTitle(), taskDto.getTitle());
    Assertions.assertEquals(task.isCompleted(), taskDto.isCompleted());
  }
}