package com.example.demo.dto.mapper;

import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
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