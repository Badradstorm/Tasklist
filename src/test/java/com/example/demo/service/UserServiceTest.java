package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.demo.BasicTest;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class UserServiceTest extends BasicTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private EntityConverter converter;

  private UserService userService;
  private User user;

  @BeforeEach
  void setUp() {
    userService = new UserService(userRepository, converter);
    user = new User();
    user.setUsername("1");
    user.setId(1);
  }

  @Test
  void testCreate() throws UserAlreadyExistsException {
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    when(userRepository.existsById(anyInt())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(new User());

    userService.create(user);

    verify(userRepository).findByUsername(anyString());
    verify(userRepository).existsById(anyInt());
    verify(userRepository).save(any(User.class));
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testCreateUserAlreadyExistsByUsername() {
    when(userRepository.findByUsername(anyString())).thenReturn(new User());

    Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
  }

  @Test
  void testCreateUserAlreadyExistsById() {
    when(userRepository.findByUsername("1")).thenReturn(new User());
    when(userRepository.existsById(1)).thenReturn(true);

    Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
  }

  @Test
  void testGetAll() {
    when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
    when(converter.toDto(any(User.class))).thenReturn(new UserDto());

    userService.getAll();

    verify(userRepository).findAll();
    verify(converter, times(2)).toDto(any(User.class));
    verifyNoMoreInteractions(converter);
  }

  @Test
  void testGetAllNotFound() {
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    when(converter.toDto(any(User.class))).thenReturn(new UserDto());

    userService.getAll();

    verify(userRepository).findAll();
    verifyNoMoreInteractions(converter);
  }

  @Test
  void testGetOne() throws UserNotFoundException {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
    when(converter.toDto(any(User.class))).thenReturn(new UserDto());

    userService.getOne(anyInt());

    verify(userRepository).findById(anyInt());
    verify(converter).toDto(any(User.class));
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testGetOneUserNotFound() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getOne(anyInt()));
  }

  @Test
  void delete() {
    doNothing().when(userRepository).deleteById(anyInt());

    userService.delete(anyInt());

    verify(userRepository).deleteById(anyInt());
    verifyNoMoreInteractions(userRepository);
  }
}