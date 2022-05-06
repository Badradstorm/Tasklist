package com.badradstorm.tasklist.dto.request;

import lombok.Data;

@Data
public class AuthRequest {

  private String username;
  private String password;
}
