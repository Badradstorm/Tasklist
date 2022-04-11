package com.example.demo.dto.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(uses = {TaskMapper.class}, componentModel = "spring")
public interface UserMapper {

  UserDto toDto (User user);
}
