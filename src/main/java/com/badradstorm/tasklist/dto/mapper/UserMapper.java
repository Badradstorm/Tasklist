package com.badradstorm.tasklist.dto.mapper;

import com.badradstorm.tasklist.dto.response.UserDto;
import com.badradstorm.tasklist.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        TaskMapper.class,
        StatusMapper.class,
        RoleMapper.class
    })
public interface UserMapper {

  @Mapping(source = "user.status", target = "isActive")
  @Mapping(source = "user.role", target = "authorities")
  UserDto toDto(User user);
}
