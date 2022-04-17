package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.demo.BaseMockTest;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class TaskServiceTest extends BaseMockTest {

  @Mock
  private TaskRepository taskRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private EntityConverter converter;

  private TaskService service;

  @BeforeEach
  void setUp() {
    service = new TaskService(taskRepository, userRepository, converter);
  }

  @Test
  public void testCreate() throws UserNotFoundException {
    User user = new User();
    user.setTaskList(new ArrayList<>());

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.create(new Task(), anyInt());

    verify(userRepository).findById(anyInt());
    verify(taskRepository).save(any(Task.class));
    verify(converter).toDto(any(Task.class));
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testCreateUserNotFound() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> service.create(new Task(), 1));
  }

  @Test
  public void testUpdate() throws UserNotFoundException, TaskNotFoundException {
    Task task = new Task();
    task.setTitle("title");
    User user = new User();
    user.setTaskList(List.of(task));

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.update(task, anyInt());

    verify(userRepository).findById(anyInt());
    verify(taskRepository).save(any(Task.class));
    verify(converter).toDto(any(Task.class));
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testUpdateUserNotFound() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> service.update(new Task(), 1));
  }

  @Test
  public void testUpdateTaskNotFound() {
    Task task = new Task();
    task.setTitle("title");
    User user = new User();
    user.setTaskList(new ArrayList<>());

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    Assertions.assertThrows(TaskNotFoundException.class, () -> service.update(task, anyInt()));
  }

  @Test
  public void testDelete() {
    doNothing().when(taskRepository).deleteById(anyInt());

    service.delete(anyInt());

    verify(taskRepository).deleteById(anyInt());
    verifyNoMoreInteractions(taskRepository);
  }
}