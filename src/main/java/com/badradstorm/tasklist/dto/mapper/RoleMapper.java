package com.badradstorm.tasklist.dto.mapper;

import com.badradstorm.tasklist.entity.Role;
import java.util.Set;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

  default Set<SimpleGrantedAuthority> map(Role role) {
    return role.getAuthorities();
  }
}
