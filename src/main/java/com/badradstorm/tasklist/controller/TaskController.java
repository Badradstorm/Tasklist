package com.badradstorm.tasklist.controller;

import com.badradstorm.tasklist.entity.Task;
import com.badradstorm.tasklist.exception.TaskNotFoundException;
import com.badradstorm.tasklist.service.TaskService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/task")
public class TaskController extends BaseController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('tasks:read')")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(taskService.getAll());
  }

  @GetMapping("{taskId}")
  @PreAuthorize("hasAuthority('tasks:read')")
  public ResponseEntity<?> getOne(@PathVariable @NotNull int taskId) throws TaskNotFoundException {
    return ResponseEntity.ok(taskService.getOne(taskId));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('tasks:write')")
  public ResponseEntity<?> create(@Valid @RequestBody Task task) {
    return responseWithLocation(taskService.create(task));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('tasks:write')")
  public ResponseEntity<?> update(@Valid @RequestBody Task task)
      throws TaskNotFoundException {
    return responseWithLocation(taskService.update(task));
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('tasks:write')")
  public ResponseEntity<?> delete(@RequestParam @NotNull int taskId) throws TaskNotFoundException {
    return ResponseEntity.ok(String.format("Задача с id %s успешно удалена!", taskService.delete(taskId)));
  }
}
