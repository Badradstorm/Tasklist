package com.badradstorm.tasklist.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

  private String username;
  private String password;
}
