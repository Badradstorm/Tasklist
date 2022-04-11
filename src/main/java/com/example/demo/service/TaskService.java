package com.example.demo.service;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.Task;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final EntityConverter converter;

  public TaskService(TaskRepository taskRepository, UserRepository userRepository,
      EntityConverter converter) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
    this.converter = converter;
  }

  public TaskDto create(Task task, int userId) throws UserNotFoundException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    task.setUser(user);
    return converter.toDto(taskRepository.save(task));
  }

  public TaskDto complete(int id) throws TaskNotFoundException {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
    task.setCompleted(!task.isCompleted());
    return converter.toDto(taskRepository.save(task));
  }
}
