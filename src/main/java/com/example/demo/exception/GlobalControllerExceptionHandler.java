package com.example.demo.exception;

import com.example.demo.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler({
      TodoNotFoundException.class,
      UserAlreadyExistsException.class,
      UserNotFoundException.class})
  public ResponseEntity<Response> handleException(Exception e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
