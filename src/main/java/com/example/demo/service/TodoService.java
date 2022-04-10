package com.example.demo.service;

import com.example.demo.dto.TodoDto;
import com.example.demo.dto.converter.EntityConverter;
import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;
  private final EntityConverter converter;

  public TodoService(TodoRepository todoRepository, UserRepository userRepository,
      EntityConverter converter) {
    this.todoRepository = todoRepository;
    this.userRepository = userRepository;
    this.converter = converter;
  }

  public TodoDto create(Todo todo, int userId) throws UserNotFoundException {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    todo.setUser(user);
    return converter.toDto(todoRepository.save(todo));
  }

  public TodoDto complete(int id) throws TodoNotFoundException {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new TodoNotFoundException("Задача не найдена"));
    todo.setCompleted(!todo.isCompleted());
    return converter.toDto(todoRepository.save(todo));
  }
}
