package com.badradstorm.tasklist.dto.mapper;

import com.badradstorm.tasklist.dto.TaskDto;
import com.badradstorm.tasklist.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskDto toDto(Task todo);
}
