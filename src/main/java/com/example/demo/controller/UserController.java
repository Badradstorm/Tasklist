package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAll() {
      return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("{id}")
  public ResponseEntity getOne(@PathVariable int id) {
    try {
      return ResponseEntity.ok(service.getOne(id));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("Пользователь не найден");
    }
  }

  @PostMapping
  public ResponseEntity create(@RequestBody User user) {
    try {
      service.create(user);
      return ResponseEntity.ok("Пользователь успешно сохранен");
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable int id) {
    try {
      return ResponseEntity.ok(service.delete(id));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("Пользователь не найден");
    }
  }
}
