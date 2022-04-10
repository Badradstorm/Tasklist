package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
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
        .map(UserDto::toDto)
        .collect(Collectors.toList());
  }

  public UserDto getOne(int id) throws UserNotFoundException {
    User user = repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    return UserDto.toDto(user);
  }

  public int delete(int id) {
    repository.deleteById(id);
    return id;
  }
}
