package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.User;
import com.example.demo.exception.UsernameAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final EntityConverter converter;

  public UserService(UserRepository repository,
      EntityConverter converter) {
    this.userRepository = repository;
    this.converter = converter;
  }

  public UserDto create(User user) throws UsernameAlreadyExistsException {
    checkExistsUsername(user.getUsername());
    return converter.toDto(userRepository.save(user));
  }

  @Cacheable("users")
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(converter::toDto)
        .collect(Collectors.toList());
  }

  @Cacheable("users")
  public UserDto getOne(int id) throws UserNotFoundException {
    return converter.toDto(get(id));
  }

  @CachePut("users")
  public UserDto update(User user) throws UserNotFoundException, UsernameAlreadyExistsException {
    User userFromDb = get(user.getId());
    checkExistsUsername(user.getUsername());
    userFromDb.setUsername(user.getUsername());
    userFromDb.setPassword(user.getPassword());
    return converter.toDto(userRepository.save(userFromDb));
  }

  @CacheEvict("users")
  public int delete(int id) {
    userRepository.deleteById(id);
    return id;
  }

  private User get(int id) throws UserNotFoundException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
  }

  private void checkExistsUsername(String username) throws UsernameAlreadyExistsException {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует!");
    }
  }
}
