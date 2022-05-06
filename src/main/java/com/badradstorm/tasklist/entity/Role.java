package com.badradstorm.tasklist.entity;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {
  USER(Set.of(
      Permission.TASKS_READ,
      Permission.TASKS_WRITE,
      Permission.USERS_UPDATE)),
  ADMIN(Set.of(
      Permission.USERS_READ,
      Permission.USERS_WRITE,
      Permission.USERS_DELETE,
      Permission.USERS_UPDATE,
      Permission.TASKS_READ,
      Permission.TASKS_WRITE));

  private final Set<Permission> permissions;

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<SimpleGrantedAuthority> getAuthorities() {
    return getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
  }
}
