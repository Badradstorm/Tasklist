package com.example.demo.exception;

public class UsernameAlreadyExistsException extends Exception{

  public UsernameAlreadyExistsException(String message) {
    super(message);
  }
}
