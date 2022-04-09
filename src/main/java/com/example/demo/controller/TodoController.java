package com.example.demo.controller;

import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {

  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping
  public ResponseEntity create(@RequestBody Todo todo, @RequestParam int userId) {
    try {
      return ResponseEntity.ok(todoService.create(todo, userId));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("Пользователь не найден");
    }
  }

  @PutMapping
  public ResponseEntity complete(@RequestParam int id) {
    try {
      return ResponseEntity.ok(todoService.complete(id));
    } catch (TodoNotFoundException e) {
      return ResponseEntity.badRequest().body("Задача не найдена");
    }
  }
}
