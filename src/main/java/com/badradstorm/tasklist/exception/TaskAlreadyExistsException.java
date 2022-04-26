package com.badradstorm.tasklist.exception;

public class TaskAlreadyExistsException extends Exception {

  public TaskAlreadyExistsException(String message) {
    super(message);
  }
}
