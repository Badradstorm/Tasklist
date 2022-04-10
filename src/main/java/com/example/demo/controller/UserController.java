package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.User;
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
  public ResponseEntity<?> getOne(@PathVariable int id) throws UserNotFoundException {
    return ResponseEntity.ok(service.getOne(id));
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody User user) throws UserAlreadyExistsException {
    service.create(user);
    return ResponseEntity.ok("Пользователь успешно сохранен");
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable int id) {
    return ResponseEntity.ok(service.delete(id));
  }
}
