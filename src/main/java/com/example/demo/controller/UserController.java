package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
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
  public ResponseEntity<?> getOne(@PathVariable @NotNull int id) throws UserNotFoundException {
    return ResponseEntity.ok(service.getOne(id));
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody User user) throws UserAlreadyExistsException {
    UserDto userDto = service.create(user);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userDto.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable @NotNull int id) {
    return ResponseEntity.ok(service.delete(id));
  }
}
