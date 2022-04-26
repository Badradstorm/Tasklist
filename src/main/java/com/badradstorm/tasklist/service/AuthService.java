package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.dto.AuthRequestDto;
import com.badradstorm.tasklist.dto.AuthResponse;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.JwtAuthenticationException;
import com.badradstorm.tasklist.repository.UserRepository;
import com.badradstorm.tasklist.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthService(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public AuthResponse authenticate(AuthRequestDto request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

      User user = userRepository.findByUsername(request.getUsername())
          .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
      String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());

      return new AuthResponse(user.getUsername(), token);
    } catch (AuthenticationException e) {
      throw new JwtAuthenticationException("Неверный логин/пароль");
    }
  }
}
