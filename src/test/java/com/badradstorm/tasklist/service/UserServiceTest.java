package com.badradstorm.tasklist.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.badradstorm.tasklist.BaseMockTest;
import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.UsernameAlreadyExistsException;
import com.badradstorm.tasklist.exception.UserNotFoundException;
import com.badradstorm.tasklist.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class UserServiceTest extends BaseMockTest {

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
  void testCreate() throws UsernameAlreadyExistsException {
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(new User());
//    when(converter.toDto(any(User.class))).thenReturn(
//        new UserDto("", "", true, Collections.emptyList(), Collections.emptyList()));

    userService.create(user);

    verify(userRepository).existsByUsername(anyString());
    verify(userRepository).save(any(User.class));
    verify(converter).toDto(any(User.class));
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testCreateUserAlreadyExists() {
    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> userService.create(user));
  }

  @Test
  void testGetAll() {
    when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
//    when(converter.toDto(any(User.class))).thenReturn(
//        new UserDto("", "", true, Collections.emptyList(), Collections.emptyList()));

    userService.getAll();

    verify(userRepository).findAll();
    verify(converter, times(2)).toDto(any(User.class));
    verifyNoMoreInteractions(converter);
  }

  @Test
  void testGetAllNotFound() {
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
//    when(converter.toDto(any(User.class))).thenReturn(
//        new UserDto("", "", true, Collections.emptyList(), Collections.emptyList()));

    userService.getAll();

    verify(userRepository).findAll();
    verifyNoMoreInteractions(converter);
  }

  @Test
  void testGetOne() throws UserNotFoundException {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
//    when(converter.toDto(any(User.class))).thenReturn(
//        new UserDto("", "", true, Collections.emptyList(), Collections.emptyList()));

    userService.getOne(anyInt());

    verify(userRepository).findById(anyInt());
    verify(converter).toDto(any(User.class));
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testGetOneNotFound() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getOne(anyInt()));
  }

  @Test
  void testDelete() {
    doNothing().when(userRepository).deleteById(anyInt());

    userService.delete(anyInt());

    verify(userRepository).deleteById(anyInt());
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testUpdate() throws UserNotFoundException, UsernameAlreadyExistsException {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(new User());
//    when(converter.toDto(any(User.class))).thenReturn(
//        new UserDto("", "", true, Collections.emptyList(), Collections.emptyList()));

    userService.update(user);

    verify(userRepository).findById(anyInt());
    verify(userRepository).existsByUsername(anyString());
    verify(userRepository).save(any(User.class));
    verify(converter).toDto(any(User.class));
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void testUpdateNotFound() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getOne(anyInt()));
  }

  @Test
  void testUpdateUserAlreadyExists() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> userService.update(user));
  }
}