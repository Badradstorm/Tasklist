package com.badradstorm.tasklist.exception;

import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.dto.response.ValidationErrorResponse;
import com.badradstorm.tasklist.dto.response.ViolationResponse;
import io.jsonwebtoken.JwtException;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler({
      TaskNotFoundException.class,
      UsernameAlreadyExistsException.class,
      UserNotFoundException.class})
  public ResponseEntity<MessageResponse> handleException(Exception e) {
    MessageResponse response = new MessageResponse(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({EmptyResultDataAccessException.class})
  public ResponseEntity<MessageResponse> handleEmptyResultException() {
    MessageResponse response = new MessageResponse("Объект запроса не найден");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleConstraintValidationException(
      ConstraintViolationException e) {
    ValidationErrorResponse response = new ValidationErrorResponse();
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      response.getViolations().add(
          new ViolationResponse(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    ValidationErrorResponse response = new ValidationErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      response.getViolations().add(
          new ViolationResponse(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
