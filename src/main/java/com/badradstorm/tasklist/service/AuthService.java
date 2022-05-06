package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.dto.request.AuthRequest;
import com.badradstorm.tasklist.dto.request.SignupRequest;
import com.badradstorm.tasklist.dto.response.AuthResponse;
import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.Role;
import com.badradstorm.tasklist.entity.Status;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.JwtAuthenticationException;
import com.badradstorm.tasklist.exception.UsernameAlreadyExistsException;
import com.badradstorm.tasklist.repository.UserRepository;
import com.badradstorm.tasklist.security.JwtTokenProvider;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder encoder;

  public AuthService(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider,
      PasswordEncoder encoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.encoder = encoder;
  }

  public AuthResponse login(AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

      User user = userRepository.findByUsername(request.getUsername())
          .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

      String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());

      return new AuthResponse(user.getUsername(), token);
    } catch (AuthenticationException e) {
      throw new JwtAuthenticationException("Неверный логин/пароль", e.getCause());
    }
  }

  public MessageResponse register(SignupRequest request) throws UsernameAlreadyExistsException {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует!");
    }

    userRepository.save(new User(request.getUsername(), encoder.encode(request.getPassword()),
        Role.USER, Status.ACTIVE, Collections.emptyList()));

    return new MessageResponse("Пользователь успешно зарегистрирован!");
  }

  public MessageResponse update(SignupRequest request) {
    UserDto user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User userFromDb = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    userFromDb.setUsername(request.getUsername());
    userFromDb.setPassword(request.getPassword());

    return new MessageResponse("Имя пользователя и пароль успешно изменены!");
  }
}
