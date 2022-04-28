package com.badradstorm.tasklist.entity;

import lombok.Getter;

@Getter
public enum Permission {
  USERS_READ("users:read"),
  USERS_WRITE("users:write"),
  TASKS_READ("tasks:read"),
  TASKS_WRITE("tasks:write");

  private final String permission;

  Permission(String permission) {
    this.permission = permission;
  }
}
