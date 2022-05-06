package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.dto.response.TaskDto;
import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.TaskNotFoundException;
import com.badradstorm.tasklist.repository.TaskRepository;
import com.badradstorm.tasklist.repository.UserRepository;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public TaskDto create(Task task) {
    task.setUser(currentUser());
    return converter.toDto(taskRepository.save(task));
  }

  @CachePut("tasks")
  public TaskDto update(Task task) throws TaskNotFoundException {
    Task taskFromUser = currentUser().getTaskList().stream()
        .filter(userTask -> userTask.getTitle().equals(task.getTitle()))
        .findFirst().orElseThrow(() -> new TaskNotFoundException("Задача с таким id не найдена"));

    taskFromUser.setTitle(task.getTitle());
    taskFromUser.setCompleted(task.isCompleted());
    return converter.toDto(taskRepository.save(task));
  }

  @CacheEvict("tasks")
  public int delete(int taskId) throws TaskNotFoundException {
    findTaskDtoFromCurrentUserDto(taskId);
    taskRepository.deleteById(taskId);
    return taskId;
  }

  public List<TaskDto> getAll() {
    return currentUserDto().getTaskList();
  }

  public TaskDto getOne(int taskId) throws TaskNotFoundException {
    return findTaskDtoFromCurrentUserDto(taskId);
  }

  private UserDto currentUserDto() {
    return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  private User currentUser() {
    return userRepository.getById(currentUserDto().getId());
  }

  private TaskDto findTaskDtoFromCurrentUserDto(int taskId) throws TaskNotFoundException {
    return currentUserDto().getTaskList().stream()
        .filter(taskDto -> taskDto.getId() == taskId)
        .findFirst().orElseThrow(() -> new TaskNotFoundException("Задача с таким id не найдена"));
  }
}
