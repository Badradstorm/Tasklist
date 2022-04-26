package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.exception.UserNotFoundException;
import com.badradstorm.tasklist.repository.TaskRepository;
import com.badradstorm.tasklist.repository.UserRepository;
import com.badradstorm.tasklist.dto.TaskDto;
import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.TaskNotFoundException;
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
