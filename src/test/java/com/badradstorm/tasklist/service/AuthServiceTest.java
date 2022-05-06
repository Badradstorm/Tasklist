package com.badradstorm.tasklist.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.badradstorm.tasklist.BaseMockTest;
import com.badradstorm.tasklist.dto.request.AuthRequest;
import com.badradstorm.tasklist.dto.request.SignupRequest;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.Role;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.JwtAuthenticationException;
import com.badradstorm.tasklist.exception.UsernameAlreadyExistsException;
import com.badradstorm.tasklist.repository.UserRepository;
import com.badradstorm.tasklist.security.JwtTokenProvider;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest extends BaseMockTest {

  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserRepository userRepository;
  @Mock
  private JwtTokenProvider jwtTokenProvider;
  @Mock
  private PasswordEncoder encoder;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;

  private AuthService service;

  @BeforeEach
  void setUp() {
    service = new AuthService(authenticationManager, userRepository, jwtTokenProvider, encoder);
  }

  @Test
  void testLogin() {
    AuthRequest request = new AuthRequest();
    request.setUsername("name");
    request.setPassword("password");
    User user = new User();
    user.setUsername("name");
    user.setRole(Role.USER);

    when(authenticationManager.authenticate(
        any(UsernamePasswordAuthenticationToken.class))).thenReturn(
        new UsernamePasswordAuthenticationToken("username", "password"));
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    when(jwtTokenProvider.createToken(anyString(), anyString())).thenReturn("token");

    service.login(request);

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByUsername(anyString());
    verify(jwtTokenProvider).createToken(anyString(), anyString());
    verifyNoMoreInteractions(authenticationManager, userRepository, jwtTokenProvider);
  }

  @Test
  void testLoginAuthenticationException() {
    AuthRequest request = new AuthRequest();
    request.setUsername("name");
    request.setPassword("password");
    User user = new User();
    user.setUsername("name");
    user.setRole(Role.USER);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken("username", "password"));
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    when(jwtTokenProvider.createToken(anyString(), anyString())).thenReturn("token");

    Assertions.assertThrows(JwtAuthenticationException.class, () -> service.login(request));
  }

  @Test
  void testRegister() throws UsernameAlreadyExistsException {
    SignupRequest request = new SignupRequest();
    request.setUsername("name");
    request.setPassword("password");

    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(encoder.encode(any(CharSequence.class))).thenReturn("pass");
    when(userRepository.save(any(User.class))).thenReturn(new User());

    service.register(request);

    verify(userRepository).existsByUsername(anyString());
    verify(encoder).encode(any(CharSequence.class));
    verify(userRepository).save(any(User.class));
    verifyNoMoreInteractions(userRepository, encoder);
  }

  @Test
  void testRegisterUserAlreadyExists() {
    SignupRequest request = new SignupRequest();
    request.setUsername("name");
    request.setPassword("password");

    when(userRepository.existsByUsername(anyString())).thenReturn(true);
    when(encoder.encode(any(CharSequence.class))).thenReturn("pass");
    when(userRepository.save(any(User.class))).thenReturn(new User());

    Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> service.register(request));
  }

  @Test
  void testUpdate() {
    SignupRequest request = new SignupRequest();
    request.setUsername("name");
    request.setPassword("password");
    UserDto userDto = new UserDto("user","pass",true, Role.USER.getAuthorities(), new ArrayList<>());
    SecurityContextHolder.setContext(securityContext);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDto);
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

    service.update(request);

    verify(securityContext).getAuthentication();
    verify(authentication).getPrincipal();
    verify(userRepository).findByUsername(anyString());
    verifyNoMoreInteractions(userRepository, securityContext, authentication);
  }

  @Test
  void testUpdateUserNotFound() {
    SignupRequest request = new SignupRequest();
    request.setUsername("name");
    request.setPassword("password");
    UserDto userDto = new UserDto("user","pass",true, Role.USER.getAuthorities(), new ArrayList<>());
    SecurityContextHolder.setContext(securityContext);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDto);
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

    Assertions.assertThrows(UsernameNotFoundException.class, () -> service.update(request));
  }
}