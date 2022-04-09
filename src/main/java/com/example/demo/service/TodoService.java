package com.example.demo.service;

import com.example.demo.dto.TodoDto;
import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
    this.todoRepository = todoRepository;
    this.userRepository = userRepository;
  }

  public TodoDto create(Todo todo, int userId) throws UserNotFoundException {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    todo.setUser(user);
    return TodoDto.toDto(todoRepository.save(todo));
  }

  public TodoDto complete(int id) throws TodoNotFoundException {
    Todo todo = todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    todo.setCompleted(!todo.isCompleted());
    return TodoDto.toDto(todoRepository.save(todo));
  }
}
