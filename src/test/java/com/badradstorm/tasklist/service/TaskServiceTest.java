package com.badradstorm.tasklist.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.badradstorm.tasklist.BaseMockTest;
import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.dto.response.TaskDto;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.Role;
import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.TaskNotFoundException;
import com.badradstorm.tasklist.repository.TaskRepository;
import com.badradstorm.tasklist.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class TaskServiceTest extends BaseMockTest {

  @Mock
  private TaskRepository taskRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private EntityConverter converter;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;

  private TaskService service;

  private UserDto userDto;

  @BeforeEach
  void setUp() {
    service = new TaskService(taskRepository, userRepository, converter);
    userDto = new UserDto("user","pass",true, Role.USER.getAuthorities(), new ArrayList<>());
    SecurityContextHolder.setContext(securityContext);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDto);
  }

  @Test
  public void testCreate() {
    User user = new User();
    user.setTaskList(new ArrayList<>());

    when(userRepository.getById(anyInt())).thenReturn(user);
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.create(new Task());

    verify(userRepository).getById(anyInt());
    verify(taskRepository).save(any(Task.class));
    verify(converter).toDto(any(Task.class));
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testUpdate() throws TaskNotFoundException {
    Task task = new Task();
    task.setTitle("title");
    User user = new User();
    user.setTaskList(List.of(task));

    when(userRepository.getById(anyInt())).thenReturn(user);
    when(taskRepository.save(any(Task.class))).thenReturn(new Task());
    when(converter.toDto(any(Task.class))).thenReturn(new TaskDto());

    service.update(task);

    verify(userRepository).getById(anyInt());
    verify(taskRepository).save(any(Task.class));
    verify(converter).toDto(any(Task.class));
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testUpdateTaskNotFound() {
    Task task = new Task();
    task.setTitle("title");
    User user = new User();
    user.setTaskList(new ArrayList<>());

    when(userRepository.getById(anyInt())).thenReturn(user);

    Assertions.assertThrows(TaskNotFoundException.class, () -> service.update(task));
  }

  @Test
  public void testDelete() throws TaskNotFoundException {
    TaskDto taskDto = new TaskDto();
    taskDto.setId(1);
    userDto.getTaskList().add(taskDto);

    doNothing().when(taskRepository).deleteById(anyInt());

    service.delete(1);

    verify(taskRepository).deleteById(anyInt());
    verifyNoMoreInteractions(taskRepository);
  }

  @Test
  public void testDeleteTaskNotFound() {
    TaskDto taskDto = new TaskDto();
    userDto.getTaskList().add(taskDto);

    doNothing().when(taskRepository).deleteById(anyInt());

    Assertions.assertThrows(TaskNotFoundException.class, () -> service.delete(1));
  }

  @Test
  public void testGetAll() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDto);

    service.getAll();

    verify(securityContext).getAuthentication();
    verify(authentication).getPrincipal();
    verifyNoMoreInteractions(authentication);
  }

  @Test
  public void testGetOne() throws TaskNotFoundException {
    TaskDto taskDto = new TaskDto();
    taskDto.setId(1);
    userDto.getTaskList().add(taskDto);

    service.getOne(1);

    verify(securityContext).getAuthentication();
    verify(authentication).getPrincipal();
    verifyNoMoreInteractions(authentication);
  }

  @Test
  public void testGetOneTaskNotFound() {
    TaskDto taskDto = new TaskDto();
    userDto.getTaskList().add(taskDto);

    Assertions.assertThrows(TaskNotFoundException.class, () -> service.getOne(1));
  }
}