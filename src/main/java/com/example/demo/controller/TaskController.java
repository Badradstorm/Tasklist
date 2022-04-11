package com.example.demo.controller;

import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TaskController {

  private final TaskService todoService;

  public TaskController(TaskService taskService) {
    this.todoService = taskService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Task task, @RequestParam int userId)
      throws UserNotFoundException {
      return ResponseEntity.ok(todoService.create(task, userId));
  }

  @PutMapping
  public ResponseEntity<?> complete(@RequestParam int id) throws TaskNotFoundException {
      return ResponseEntity.ok(todoService.complete(id));
  }
}
