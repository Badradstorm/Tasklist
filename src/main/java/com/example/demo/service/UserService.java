package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
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

  public UserDto create(User user) throws UserAlreadyExistsException {
    if (userRepository.findByUsername(user.getUsername()).isPresent() || userRepository.existsById(
        user.getId())) {
      throw new UserAlreadyExistsException("Пользователь с таким именем или id уже существует!");
    }
    return converter.toDto(userRepository.save(user));
  }

  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(converter::toDto)
        .collect(Collectors.toList());
  }

  public UserDto getOne(int id) throws UserNotFoundException {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    return converter.toDto(user);
  }

  public int delete(int id) {
    userRepository.deleteById(id);
    return id;
  }
}
