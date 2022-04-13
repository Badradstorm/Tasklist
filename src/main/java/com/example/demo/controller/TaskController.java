package com.example.demo.controller;

import com.example.demo.dto.TaskDto;
import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Task task, @RequestParam @NotNull int userId)
      throws UserNotFoundException {
    TaskDto taskDto = taskService.create(task, userId);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(taskDto.getId())
        .toUri();

      return ResponseEntity.created(location).build();
  }

  @PutMapping
  public ResponseEntity<?> complete(@RequestParam @NotNull int taskId) throws TaskNotFoundException {
      return ResponseEntity.ok(taskService.complete(taskId));
  }
}
