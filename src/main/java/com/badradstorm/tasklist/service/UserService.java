package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.exception.UserNotFoundException;
import com.badradstorm.tasklist.repository.UserRepository;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.UsernameAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final EntityConverter converter;

  public UserService(UserRepository repository,
      EntityConverter converter) {
    this.userRepository = repository;
    this.converter = converter;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    return converter.toDto(user);
  }

  public UserDto create(User user) throws UsernameAlreadyExistsException {
    checkExistsUsername(user.getUsername());
    return converter.toDto(userRepository.save(user));
  }

  @Cacheable("users")
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(converter::toDto)
        .collect(Collectors.toList());
  }

  @Cacheable("users")
  public UserDto getOne(int id) throws UserNotFoundException {
    return converter.toDto(get(id));
  }

  @CachePut("users")
  public UserDto update(User user) throws UserNotFoundException, UsernameAlreadyExistsException {
    User userFromDb = get(user.getId());
    checkExistsUsername(user.getUsername());
    userFromDb.setUsername(user.getUsername());
    userFromDb.setPassword(user.getPassword());
    return converter.toDto(userRepository.save(userFromDb));
  }

  @CacheEvict("users")
  public MessageResponse delete(int id) {
    userRepository.deleteById(id);
    return new MessageResponse("Пользователь удален!");
  }

  private User get(int id) throws UserNotFoundException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
  }

  private void checkExistsUsername(String username) throws UsernameAlreadyExistsException {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует!");
    }
  }
}
