package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {

  private int id;
  private String username;
  private List<TaskDto> toDoList;
}
