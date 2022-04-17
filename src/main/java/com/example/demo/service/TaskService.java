package com.example.demo.service;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

  @Cacheable("tasks")
  public TaskDto create(Task task, int userId) throws UserNotFoundException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    task.setUser(user);
    return converter.toDto(taskRepository.save(task));
  }

  @CachePut("tasks")
  public TaskDto update(Task task, int userId) throws UserNotFoundException, TaskNotFoundException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

    Task taskFromUser = user.getTaskList().stream()
        .filter(userTask -> userTask.getTitle().equals(task.getTitle()))
        .findFirst().orElseThrow(() -> new TaskNotFoundException("Такая задача не найдена"));

    taskFromUser.setTitle(task.getTitle());
    taskFromUser.setCompleted(task.isCompleted());
    return converter.toDto(taskRepository.save(task));
  }

  @CacheEvict("tasks")
  public int delete(int id) {
    taskRepository.deleteById(id);
    return id;
  }
}
