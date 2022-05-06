package com.badradstorm.tasklist.controller;

import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.exception.UserNotFoundException;
import com.badradstorm.tasklist.service.UserService;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/user")
public class UserController{

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('users:read')")
  public ResponseEntity<List<UserDto>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('users:read')")
  public ResponseEntity<?> getOne(@PathVariable @NotNull int id) throws UserNotFoundException {
    return ResponseEntity.ok(service.getOne(id));
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('users:delete')")
  public ResponseEntity<?> delete(@PathVariable @NotNull int id) {
    return ResponseEntity.ok(service.delete(id));
  }
}
