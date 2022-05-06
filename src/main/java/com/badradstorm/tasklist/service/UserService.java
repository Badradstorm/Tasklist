package com.badradstorm.tasklist.service;

import com.badradstorm.tasklist.dto.converter.EntityConverter;
import com.badradstorm.tasklist.dto.response.MessageResponse;
import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.User;
import com.badradstorm.tasklist.exception.UserNotFoundException;
import com.badradstorm.tasklist.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
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
  @Cacheable("users")
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    return converter.toDto(user);
  }

  @Cacheable("users")
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(converter::toDto)
        .collect(Collectors.toList());
  }

  @Cacheable("users")
  public UserDto getOne(int id) throws UserNotFoundException {
    return converter.toDto(userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден")));
  }

  @CacheEvict("users")
  public MessageResponse delete(int id) {
    userRepository.deleteById(id);
    return new MessageResponse(String.format("Пользователь с id %s успешно удален!", id));
  }
}
