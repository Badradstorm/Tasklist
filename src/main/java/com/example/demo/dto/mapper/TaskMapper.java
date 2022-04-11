package com.example.demo.dto.mapper;

import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskDto toDto(Task todo);
}
