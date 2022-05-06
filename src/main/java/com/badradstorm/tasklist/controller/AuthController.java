package com.badradstorm.tasklist.controller;

import com.badradstorm.tasklist.dto.request.AuthRequest;
import com.badradstorm.tasklist.dto.request.SignupRequest;
import com.badradstorm.tasklist.exception.UsernameAlreadyExistsException;
import com.badradstorm.tasklist.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest request) {
      return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    new SecurityContextLogoutHandler().logout(request, response, null);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request)
      throws UsernameAlreadyExistsException {
    return ResponseEntity.ok(authService.register(request));
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@Valid @RequestBody SignupRequest request) {
    return ResponseEntity.ok(authService.update(request));
  }
}
