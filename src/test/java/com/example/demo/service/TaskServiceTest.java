package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.demo.BasicTest;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class TaskServiceTest extends BasicTest {

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
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.create(new Task(), 1);

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
  public void testComplete() throws TaskNotFoundException {
    when(taskRepository.findById(anyInt())).thenReturn(Optional.of(new Task()));
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.complete(1);

    verify(taskRepository).findById(anyInt());
    verify(taskRepository).save(any(Task.class));
    verify(converter).toDto(any(Task.class));
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testCompleteTaskNotFound() {
    when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(TaskNotFoundException.class, () -> service.complete(1));
  }
}