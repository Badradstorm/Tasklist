package com.example.demo.dto.mapper;

import com.example.demo.dto.TodoDto;
import com.example.demo.entity.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  TodoDto toDto(Todo todo);
}
