package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;
  private final EntityConverter converter;

  public UserService(UserRepository repository,
      EntityConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  public void create(User user) throws UserAlreadyExistsException {
    if (repository.findByUsername(user.getUsername()) != null || repository.existsById(
        user.getId())) {
      throw new UserAlreadyExistsException("Пользователь с таким именем или id уже существует!");
    }
    repository.save(user);
  }

  public List<UserDto> getAll() {
    return repository.findAll().stream()
        .map(converter::toDto)
        .collect(Collectors.toList());
  }

  public UserDto getOne(int id) throws UserNotFoundException {
    User user = repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    return converter.toDto(user);
  }

  public int delete(int id) {
    repository.deleteById(id);
    return id;
  }
}
